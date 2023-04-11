//package com.zhaofujun.nest3.application.context;
//
//import com.zhaofujun.nest3.CustomException;
//import com.zhaofujun.nest3.CustomExceptionable;
//import com.zhaofujun.nest3.NestCustomException;
//import com.zhaofujun.nest3.SystemException;
//import com.zhaofujun.nest3.application.CacheClient;
//import com.zhaofujun.nest3.application.NestApplication;
//import com.zhaofujun.nest3.application.config.EventConfiguration;
//import com.zhaofujun.nest3.application.event.MessageBacklog;
//import com.zhaofujun.nest3.application.event.MessageInfo;
//import com.zhaofujun.nest3.application.manager.ConfigurationManager;
//import com.zhaofujun.nest3.application.provider.MessageChannelProvider;
//import com.zhaofujun.nest3.impl.fastjson.FastjsonIteratorAndJsonBuilder;
//import com.zhaofujun.nest3.model.AggregateRoot;
//import com.zhaofujun.nest3.model.Entity;
//import com.zhaofujun.nest3.model.Identifier;
//import com.zhaofujun.nest3.model.Repository;
//import com.zhaofujun.nest3.utils.EntityUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.util.*;
//import java.util.stream.Collectors;
//
//public class EntityUnitOfWork {
//
//    private Logger logger = LoggerFactory.getLogger(EntityUnitOfWork.class);
//    private ServiceContext serviceContext;
//    private TransactionManager transactionManager;
//    private EntityPreStoreList beginEntityList = new EntityPreStoreList();
//    private EntityPreStoreList endEntityList = new EntityPreStoreList();
//
//
//    public EntityUnitOfWork(ServiceContext serviceContext, TransactionManager transactionManager) {
//        this.serviceContext = serviceContext;
//
//        if (transactionManager == null) {
//            this.transactionManager = new TransactionManager() {
//                @Override
//                public void commit(Runnable runnable) {
//                    runnable.run();
//                }
//            };
//        } else {
//            this.transactionManager = transactionManager;
//        }
//    }
//
//
//    public void addAggregateRoot(AggregateRoot aggregateRoot) {
//        this.aggregateRoots.add(aggregateRoot);
//        FastjsonIteratorAndJsonBuilder entityFinder = new FastjsonIteratorAndJsonBuilder();
//        aggregateRoot.ready(new SnapshotCreator(entityFinder, entityFinder, beginEntityList));
//    }
//
//
//    public void beforeCommit() {
//        FastjsonIteratorAndJsonBuilder entityFinder = new FastjsonIteratorAndJsonBuilder();
//
//        aggregateRoots.stream()
//                .filter(p -> !p.is__deleted()) //过滤掉需要删除的项
//                .forEach(p -> {
//                    p.end(new SnapshotCreator(entityFinder, entityFinder, endEntityList));
//                });
//    }
//
//    public Map<Repository, Map<EntityOperateEnum, Collection<Entity>>> analyse() {
//
//        List<EntityInfo> entityInfos = new ArrayList<>();
//        //找出需要被移除掉的实体，(取差集。begin-end=begin存在，end不存在的）
//
//
//        entityInfos.addAll(
//                difference(beginEntityList.getEntitySet(), endEntityList.getEntitySet()).stream()
//                        .map(p -> new EntityInfo(p, EntityOperateEnum.remove, getRepository(p)))
//                        .collect(Collectors.toList())
//        );
//        //修改的实体（取交集。begin和end都存在，但快照对比不一样,以end为最终信息，如果begin和end的实体不同是一个对象地址，可能会导致end实体没有begin快照）
//        entityInfos.addAll(
//                intersection(endEntityList.getEntitySet(), beginEntityList.getEntitySet())
//                        .stream()
//                        .filter(p -> !p.getBeginSnapshot().equals(p.getEndSnapshot()))
//                        .map(p -> new EntityInfo(p, EntityOperateEnum.update, getRepository(p)))
//                        .collect(Collectors.toList())
//        );
//        //新增的实体(取差集 end-begin= begin中不存在，但end中存在)，
//        entityInfos.addAll(
//                difference(endEntityList.getEntitySet(), beginEntityList.getEntitySet()).stream()
//                        .map(p -> new EntityInfo(p, EntityOperateEnum.create, getRepository(p)))
//                        .collect(Collectors.toList())
//        );
//        //因为新创建的聚合会满足修改的项，所以需要根据聚合列表中新增的状态的实体调整为新增状态。
//        entityInfos
//                .stream()
//                .filter(p -> p.entity instanceof AggregateRoot)
//                .filter(p -> ((AggregateRoot<?>) p.entity).is__new())
//                .forEach(p -> p.operateEnum = EntityOperateEnum.create);
//
//        //将列表按仓储分组后再按操作方式分组。
//        Map<Repository, Map<EntityOperateEnum, Collection<Entity>>> mapMap = new TreeMap<>(Comparators.getRepositoryComparator());
//        entityInfos.stream()
//                .collect(Collectors.groupingBy(p -> p.repository))
//                .forEach((p, q) -> {
//                    Map<EntityOperateEnum, Collection<Entity>> collect = q.stream()
//                            .collect(Collectors.groupingBy(m -> m.operateEnum, Collectors.mapping(n -> n.getEntity(), Collectors.toCollection(() -> new TreeSet<>(Comparators.getEntityComparator())))));
//                    mapMap.put(p, collect);
//                });
//
//        return mapMap;
//    }
//
//    //差集
//    private Set<Entity> difference(Set<Entity> s1, Set<Entity> s2) {
//        Set<Entity> result = new HashSet<>(s1);
//        result.removeAll(s2);
//        return result;
//    }
//
//    //交集
//    private Set<Entity> intersection(Set<Entity> s1, Set<Entity> s2) {
//        Set<Entity> result = new HashSet<>(s1);
//        result.retainAll(s2);
//        return result;
//    }
//
//    private Repository getRepository(Entity entity) {
//        return this.serviceContext.getNestApplication().getRepository(entity.getClass());
//    }
//
//    public <U extends Entity> U getEntity(Class<U> uClass, Identifier id) {
//        return (U) beginEntityList.getEntitySet()
//                .stream()
//                .filter(p -> p.getClass().equals(uClass) && p.getId().equals(id))
//                .findFirst().orElse(null);
//    }
//
//    private void commitEntity() {
//
//        Map<Repository, Map<EntityOperateEnum, Collection<Entity>>> repositoryMap = analyse();
////        NestApplication.current().beforeEntityCommit(serviceContext,repositoryMap);
//        CacheClient cacheClient = EntityUtils.getEntityCacheClient();
//        try {
//            this.transactionManager.commit(() -> {
//
//                repositoryMap.forEach((p, q) -> {
//                    q.forEach((r, s) -> {
//                        if (s.isEmpty()) {
//                            return;
//                        }
////                        entityNotify(r, s);
//                        switch (r) {
//                            case create:
//                                p.batchInsert(s);
//                                break;
//                            case update:
//                                p.batchUpdate(s);
//                                break;
//                            case remove:
//                                p.batchDelete(s);
//                        }
//
//                    });
//
//                });
//            });
//
//            clearCache(repositoryMap, cacheClient);
//        } catch (NestCustomException ex) {
//            if (ex.getErrorCode() == NestCustomException.VersionConflicted)
//                clearCache(repositoryMap, cacheClient);
//            throw ex;
//        }
//
//    }
//
//    private void clearCache(Map<Repository, Map<EntityOperateEnum, Collection<Entity>>> repositoryMap, CacheClient cacheClient) {
//        repositoryMap.forEach((p, q) -> {
//            q.forEach((r, s) -> {
//                if (!r.equals(EntityOperateEnum.create)) {
//                    s.forEach(ss -> cacheClient.remove(EntityUtils.getCacheKey(ss)));
//                }
//            });
//        });
//    }
//
//    void commit() {
//
////        NestApplication.current().beforeCommit(serviceContext);
//
//        try {
//            beforeCommit();
//
//            commitEntity();
//        } catch (CustomException ex) {
//            throw ex;
//        } catch (Exception ex) {
//            if (ex instanceof CustomExceptionable) {
//
//                NestCustomException customException = new NestCustomException(NestCustomException.Other, ex.getMessage(), ex);
//                throw customException;
//            }
//            throw new SystemException("提交工作单元时失败" + ex.getMessage(), ex);
//        } finally {
////            entities.clear();
//            aggregateRoots.clear();
//            beginEntityList.clear();
//            endEntityList.clear();
//        }
//
////        //开始提交消息到消息中间件
////        try {
////            commitMessage();
////        } catch (CustomException ex) {
////            throw ex;
////        } catch (Exception ex) {
////
////            if (ex instanceof CustomExceptionable) {
////                //业务异常
////                OtherCustomException customException = new OtherCustomException(ex.getMessage(), ex);
////                throw customException;
////            }
////
////            throw new SystemException("提交工作单元时失败", ex);
////        } finally {
////            messageBacklogs.clear();
////            messageBacklogs.clear();
////        }
////
////        NestApplication.current().committed(serviceContext);
//    }
//
//    class EntityInfo {
//        private Entity entity;
//        private EntityOperateEnum operateEnum;
//        private Repository repository;
//
//        public EntityInfo(Entity entity, EntityOperateEnum operateEnum, Repository repository) {
//            this.entity = entity;
//            this.operateEnum = operateEnum;
//            this.repository = repository;
//        }
//
//        public Entity getEntity() {
//            return entity;
//        }
//
//        public EntityOperateEnum getOperateEnum() {
//            return operateEnum;
//        }
//
//        public Repository getRepository() {
//            return repository;
//        }
//    }
//
//    public enum EntityOperateEnum {
//        create, update, remove
//    }
//
//}
//
