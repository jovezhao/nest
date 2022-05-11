package com.zhaofujun.nest.context.appservice;

import com.zhaofujun.nest.NestApplication;
import com.zhaofujun.nest.cache.CacheClient;
import com.zhaofujun.nest.cache.CacheClientFactory;
import com.zhaofujun.nest.configuration.ConfigurationManager;
import com.zhaofujun.nest.context.event.EventConfiguration;
import com.zhaofujun.nest.context.event.channel.MessageChannelProviderFactory;
import com.zhaofujun.nest.context.event.channel.distribute.DistributeMessageChannel;
import com.zhaofujun.nest.context.event.delay.DelayMessageBacklog;
import com.zhaofujun.nest.context.event.delay.DelayMessageStore;
import com.zhaofujun.nest.context.event.delay.DelayMessageStoreFactory;
import com.zhaofujun.nest.context.event.message.MessageBacklog;
import com.zhaofujun.nest.context.event.message.MessageConverterFactory;
import com.zhaofujun.nest.context.event.message.MessageInfo;
import com.zhaofujun.nest.context.event.resend.MessageResendFactory;
import com.zhaofujun.nest.context.event.resend.MessageResendStore;
import com.zhaofujun.nest.context.model.BaseEntity;
import com.zhaofujun.nest.context.model.EntityNotify;
import com.zhaofujun.nest.exception.OtherCustomException;
import com.zhaofujun.nest.exception.VersionConflictedException;
import com.zhaofujun.nest.standard.*;
import com.zhaofujun.nest.utils.EntityCacheUtils;
import com.zhaofujun.nest.utils.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class UnitOfWork {

    private Logger logger = LoggerFactory.getLogger(UnitOfWork.class);
    private ServiceContext serviceContext;
    private TransactionManager transactionManager;


    UnitOfWork(ServiceContext serviceContext, TransactionManager transactionManager) {
        this.serviceContext = serviceContext;

        if (transactionManager == null) {
            this.transactionManager = new TransactionManager() {
                @Override
                public void commit(Runnable runnable) {
                    runnable.run();
                }
            };
        } else {
            this.transactionManager = transactionManager;
        }
    }

    private Set<BaseEntity> entities = new HashSet<>();


    public <T extends BaseEntity> T getEntity(Class tClass, Identifier identifier) {
        Entity entity = entities
                .stream()
                .filter(p -> identifier.equals(p.getId()) && tClass.isInstance(p))
                .filter(p -> !p.is__deleted())
                .findFirst()
                .orElse(null);
        return (T) entity;
    }


    public void put(BaseEntity entity) {
        if (entity != null)
            entities.add(entity);
    }

    private void commitEntity() {

        Map<Repository, Map<EntityOperateEnum, Collection<BaseEntity>>> repositoryMap = toRepositoryMap();
        NestApplication.current().beforeEntityCommit(serviceContext,repositoryMap);
        CacheClient cacheClient = CacheClientFactory.getCacheClient(EntityCacheUtils.getCacheCode());
        try {
            this.transactionManager.commit(() -> {

                repositoryMap.forEach((p, q) -> {
                    q.forEach((r, s) -> {
                        if (s.isEmpty()) {
                            return;
                        }
                        entityNotify(r, s);
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
            });

            clearCache(repositoryMap, cacheClient);
        } catch (VersionConflictedException ex) {
            clearCache(repositoryMap, cacheClient);
            throw ex;
        }

    }

    private void clearCache(Map<Repository, Map<EntityOperateEnum, Collection<BaseEntity>>> repositoryMap, CacheClient cacheClient) {
        repositoryMap.forEach((p, q) -> {
            q.forEach((r, s) -> {
                if (!r.equals(EntityOperateEnum.create)) {
                    s.forEach(ss -> cacheClient.remove(EntityCacheUtils.getCacheKey(ss)));
                }
            });
        });
    }

    private Set<MessageBacklog> messageBacklogs = new HashSet<>();


    private Set<DelayMessageBacklog> delayMessageBacklogs = new HashSet<>();

    public void addMessageBacklog(String eventCode, MessageInfo messageInfo) {
        String messageInfoString = MessageConverterFactory.create().messageToString(messageInfo);
        messageBacklogs.add(new MessageBacklog(eventCode, messageInfoString,messageInfo.getData().getClass().getName(),messageInfo.getMessageId()));
    }

    private void commitMessage() {
        NestApplication.current().beforeMessageCommit(serviceContext,messageBacklogs);
        messageBacklogs.forEach(p -> {
            ConfigurationManager configurationManager = NestApplication.current().getConfigurationManager();
            EventConfiguration eventConfiguration = configurationManager.getEventConfigurationByEventCode(p.getEventCode());

            DistributeMessageChannel messageChannel = (DistributeMessageChannel) MessageChannelProviderFactory.create(eventConfiguration.getMessageChannelCode());
            try {
                messageChannel.getMessageProducer().commit(p.getEventCode(),p.getMessageId() , p.getMessageInfoString());
            } catch (Exception ex) {
                //投递到消息中间件时发生异常，将有异常的数据存入待发送区域，用于消息补偿
                logger.warn("提交消息时失败，消息将通过补偿器重试，失败原因：" + ex.getMessage(), ex);

                MessageResendStore messageResendStore = MessageResendFactory.create();
                messageResendStore.add(p);
            }
        });
        delayMessageBacklogs.forEach(p -> {
            try {
                DelayMessageStore delayMessageStore = DelayMessageStoreFactory.create();
                delayMessageStore.add(p);
            } catch (Exception ex) {
                //考虑一般情况下，DelayMessageStore和MessageResendStore存储中间件相同，所以加到待发送区域也应该会失败，所以暂不做重试逻辑
                logger.warn("提交延时消息失败，失败原因", ex);
            }
        });
    }

    void commit() {

        NestApplication.current().beforeCommit(serviceContext);

        try {
            commitEntity();
        } catch (CustomException ex) {
            throw ex;
        } catch (Exception ex) {
            if (ex instanceof CustomExceptionable) {

                OtherCustomException customException = new OtherCustomException(ex.getMessage(), ex);
                throw customException;
            }
            throw new SystemException("提交工作单元时失败" + ex.getMessage(), ex);
        } finally {
            entities.clear();
        }

        //开始提交消息到消息中间件
        try {
            commitMessage();
        } catch (CustomException ex) {
            throw ex;
        } catch (Exception ex) {

            if (ex instanceof CustomExceptionable) {
                //业务异常
                OtherCustomException customException = new OtherCustomException(ex.getMessage(), ex);
                throw customException;
            }

            throw new SystemException("提交工作单元时失败", ex);
        } finally {
            messageBacklogs.clear();
            delayMessageBacklogs.clear();
        }

        NestApplication.current().committed(serviceContext);
    }


    private Map<Repository, Map<EntityOperateEnum, Collection<BaseEntity>>> toRepositoryMap() {
        Map<Repository, Map<EntityOperateEnum, Collection<BaseEntity>>> repositoryMap = new TreeMap<>(Comparators.getRepositoryComparator());

        entities.forEach(p -> p.end());

        entities.stream()
                .filter(p -> !(p.is__new() && p.is__deleted()))//排除又是新增又被删除的
                .filter(p -> !(!p.isChanged() && !p.is__new() && !p.is__deleted())) //排除没有发生改变并且不是新增类型还没有被删除的
                .forEach(p -> {
                    Repository repository = NestApplication.current().getRepositoryManager().create(p.getClass());

                    Map<EntityOperateEnum, Collection<BaseEntity>> entityOperateEnumListMap = repositoryMap.get(repository);
                    if (!repositoryMap.containsKey(repository)) {
                        entityOperateEnumListMap = new HashMap<>();
                        entityOperateEnumListMap.put(EntityOperateEnum.create, new TreeSet<>(Comparators.getBaseEntityComparator()));
                        entityOperateEnumListMap.put(EntityOperateEnum.update, new TreeSet<>(Comparators.getBaseEntityComparator()));
                        entityOperateEnumListMap.put(EntityOperateEnum.remove, new TreeSet<>(Comparators.getBaseEntityComparator()));
                        repositoryMap.put(repository, entityOperateEnumListMap);
                    }

                    if (p.is__new()) {
                        Collection<BaseEntity> entityList = entityOperateEnumListMap.get(EntityOperateEnum.create);
                        entityList.add(p);
                    } else if (p.is__deleted()) {
                        Collection<BaseEntity> entityList = entityOperateEnumListMap.get(EntityOperateEnum.remove);
                        entityList.add(p);
                    } else {
                        Collection<BaseEntity> entityList = entityOperateEnumListMap.get(EntityOperateEnum.update);
                        entityList.add(p);
                    }


                });


        return repositoryMap;
    }


    private void entityNotify(EntityOperateEnum operateEnum, Collection<BaseEntity> entities) {
        entities.forEach(baseEntity -> {

            if (baseEntity instanceof EntityNotify) {

                MessageInfo<EntityNotifyEventData> messageInfo = new MessageInfo<>();

                EntityNotifyEventData eventData = new EntityNotifyEventData();
                eventData.setEntityClassName(baseEntity.getClassName());
                eventData.setBeginSnapshot(baseEntity.getBeginSnapshot());
                eventData.setEndSnapshot(baseEntity.getEndSnapshot());
                eventData.setServiceName(serviceContext.getServiceClass().getName());
                eventData.setMethodName(serviceContext.getMethod());
                eventData.setOperateEnum(operateEnum);

                messageInfo.setMessageId(UUID.randomUUID().toString());
                messageInfo.setData(eventData);
                messageInfo.setEventSource("nest");
                messageInfo.setSendTime(new Date());

                addMessageBacklog(eventData.getEventCode(), messageInfo);
            }
        });


    }

    public void addDelayMessageBacklog(DelayMessageBacklog delayMessageBacklog) {
        delayMessageBacklogs.add(delayMessageBacklog);
    }
}


