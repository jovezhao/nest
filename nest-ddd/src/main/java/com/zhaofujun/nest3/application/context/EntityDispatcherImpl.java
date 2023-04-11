package com.zhaofujun.nest3.application.context;

import com.zhaofujun.nest3.NestCustomException;
import com.zhaofujun.nest3.application.CacheClient;
import com.zhaofujun.nest3.impl.fastjson.FastjsonIteratorAndJsonBuilder;
import com.zhaofujun.nest3.model.AggregateRoot;
import com.zhaofujun.nest3.model.Entity;
import com.zhaofujun.nest3.model.Identifier;
import com.zhaofujun.nest3.model.Repository;
import com.zhaofujun.nest3.utils.EntityUtils;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class EntityDispatcherImpl implements EntityDispatcher {
    private ServiceContext serviceContext;
    private TransactionManager transactionManager;
    private CacheClient cacheClient=EntityUtils.getEntityCacheClient();;
    private Consumer<Set<Entity>> beforeExecuteCallback;
    private EntityPreStoreList beginEntityList = new EntityPreStoreList();
    private EntityPreStoreList endEntityList = new EntityPreStoreList();
    private FastjsonIteratorAndJsonBuilder entityFinder = new FastjsonIteratorAndJsonBuilder();
    private Consumer<Set<Entity>> executeCallback;
    private Consumer<Set<Entity>> committedCallback;
    private Consumer<Set<Entity>> failCallback;


    public EntityDispatcherImpl(ServiceContext serviceContext, TransactionManager transactionManager) {
        this.serviceContext = serviceContext;
        this.transactionManager = transactionManager;
    }

    public EntityDispatcherImpl(ServiceContext serviceContext, TransactionManager transactionManager, Consumer<Set<Entity>> beforeExecuteCallback, Consumer<Set<Entity>> executeCallback, Consumer<Set<Entity>> committedCallback, Consumer<Set<Entity>> failCallback) {
        this.serviceContext = serviceContext;
        this.transactionManager = transactionManager;
        this.beforeExecuteCallback = beforeExecuteCallback;
        this.executeCallback = executeCallback;
        this.committedCallback = committedCallback;
        this.failCallback = failCallback;
    }

    @Override
    public void initRoot(AggregateRoot aggregateRoot) {
        aggregateRoot.ready(new SnapshotCreator(entityFinder, entityFinder, beginEntityList));
    }

    @Override
    public void endRoot(AggregateRoot aggregateRoot) {
        aggregateRoot.end(new SnapshotCreator(entityFinder, entityFinder, endEntityList));
    }


    private Map<Repository, Map<EntityOperateEnum, Collection<Entity>>> analyse() {
        List<EntityInfo> entityInfos = new ArrayList<>();
        //找出需要被移除掉的实体，(取差集。begin-end=begin存在，end不存在的）

        entityInfos.addAll(
                difference(beginEntityList.getEntitySet(), endEntityList.getEntitySet()).stream()
                        .map(p -> new EntityInfo(p, EntityOperateEnum.remove, getRepository(p)))
                        .collect(Collectors.toList())
        );
        //修改的实体（取交集。begin和end都存在，但快照对比不一样,以end为最终信息，如果begin和end的实体不同是一个对象地址，可能会导致end实体没有begin快照）
        entityInfos.addAll(
                intersection(endEntityList.getEntitySet(), beginEntityList.getEntitySet())
                        .stream()
                        .filter(p -> !p.getBeginSnapshot().equals(p.getEndSnapshot()))
                        .map(p -> new EntityInfo(p, EntityOperateEnum.update, getRepository(p)))
                        .collect(Collectors.toList())
        );
        //新增的实体(取差集 end-begin= begin中不存在，但end中存在)，
        entityInfos.addAll(
                difference(endEntityList.getEntitySet(), beginEntityList.getEntitySet()).stream()
                        .map(p -> new EntityInfo(p, EntityOperateEnum.create, getRepository(p)))
                        .collect(Collectors.toList())
        );
        //因为新创建的聚合会满足修改的项，所以需要根据聚合列表中新增的状态的实体调整为新增状态。
        entityInfos
                .stream()
                .filter(p -> p.entity instanceof AggregateRoot)
                .filter(p -> ((AggregateRoot<?>) p.entity).is__new())
                .forEach(p -> p.operateEnum = EntityOperateEnum.create);

        //将列表按仓储分组后再按操作方式分组。
        Map<Repository, Map<EntityOperateEnum, Collection<Entity>>> mapMap = new TreeMap<>(Comparators.getRepositoryComparator());
        entityInfos.stream()
                .collect(Collectors.groupingBy(p -> p.repository))
                .forEach((p, q) -> {
                    Map<EntityOperateEnum, Collection<Entity>> collect = q.stream()
                            .collect(Collectors.groupingBy(m -> m.operateEnum, Collectors.mapping(n -> n.getEntity(), Collectors.toCollection(() -> new TreeSet<>(Comparators.getEntityComparator())))));
                    mapMap.put(p, collect);
                });

        return mapMap;
    }

    private Repository getRepository(Entity entity) {
        return this.serviceContext.getNestApplication().getRepository(entity.getClass());
    }

    //差集
    private Set<Entity> difference(Set<Entity> s1, Set<Entity> s2) {
        Set<Entity> result = new HashSet<>(s1);
        result.removeAll(s2);
        return result;
    }

    //交集
    private Set<Entity> intersection(Set<Entity> s1, Set<Entity> s2) {
        Set<Entity> result = new HashSet<>(s1);
        result.retainAll(s2);
        return result;
    }

    private void clearCache(Map<Repository, Map<EntityOperateEnum, Collection<Entity>>> repositoryMap, CacheClient cacheClient) {
        repositoryMap.forEach((p, q) -> {
            q.forEach((r, s) -> {
                if (!r.equals(EntityOperateEnum.create)) {
                    s.forEach(ss -> cacheClient.remove(EntityUtils.getCacheKey(ss)));
                }
            });
        });
    }

    @Override
    public void dispatch() {

        if (beforeExecuteCallback != null) beforeExecuteCallback.accept(endEntityList.getEntitySet());

        Map<Repository, Map<EntityOperateEnum, Collection<Entity>>> repositoryMap = analyse();
//        NestApplication.current().beforeEntityCommit(serviceContext,repositoryMap);

        try {
            this.transactionManager.commit(() -> {

                repositoryMap.forEach((p, q) -> {
                    q.forEach((r, s) -> {
                        if (s.isEmpty()) {
                            return;
                        }
//                        entityNotify(r, s);
                        switch (r) {
                            case create:
                                p.batchInsert(s);
                                break;
                            case update:
                                p.batchUpdate(s);
                                break;
                            case remove:
                                p.batchDelete(s);
                        }

                    });

                });

                if (executeCallback != null) executeCallback.accept(endEntityList.getEntitySet());
            });

            clearCache(repositoryMap, cacheClient);
        } catch (NestCustomException ex) {
            if (ex.getErrorCode() == NestCustomException.VersionConflicted)
                clearCache(repositoryMap, cacheClient);
            if (failCallback != null) failCallback.accept(endEntityList.getEntitySet());
            throw ex;
        } catch (Exception exception) {
            if (failCallback != null) failCallback.accept(endEntityList.getEntitySet());
            throw exception;
        }
        if (committedCallback != null) committedCallback.accept(endEntityList.getEntitySet());
    }

    public <U extends Entity> U getEntity(Class<U> uClass, Identifier id) {
        return (U) beginEntityList.getEntitySet()
                .stream()
                .filter(p -> p.getClass().equals(uClass) && p.getId().equals(id))
                .findFirst().orElse(null);
    }

    @Override
    public void setBeforeExecuteCallback(Consumer<Set<Entity>> beforeExecuteCallback) {
        this.beforeExecuteCallback = beforeExecuteCallback;
    }

    @Override
    public void setExecutedCallback(Consumer<Set<Entity>> executeCallback) {
        this.executeCallback = executeCallback;
    }

    @Override
    public void setCommittedCallback(Consumer<Set<Entity>> committedCallback) {
        this.committedCallback = committedCallback;
    }

    @Override
    public void setFailCallback(Consumer<Set<Entity>> failCallback) {
        this.failCallback = failCallback;
    }


    class EntityInfo {
        private Entity entity;
        private EntityOperateEnum operateEnum;
        private Repository repository;

        public EntityInfo(Entity entity, EntityOperateEnum operateEnum, Repository repository) {
            this.entity = entity;
            this.operateEnum = operateEnum;
            this.repository = repository;
        }

        public Entity getEntity() {
            return entity;
        }

        public EntityOperateEnum getOperateEnum() {
            return operateEnum;
        }

        public Repository getRepository() {
            return repository;
        }
    }

    public enum EntityOperateEnum {
        create, update, remove
    }
}
