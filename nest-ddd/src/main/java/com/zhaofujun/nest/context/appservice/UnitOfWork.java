package com.zhaofujun.nest.context.appservice;

import com.zhaofujun.nest.NestApplication;
import com.zhaofujun.nest.cache.CacheClient;
import com.zhaofujun.nest.cache.CacheClientFactory;
import com.zhaofujun.nest.configuration.ConfigurationManager;
import com.zhaofujun.nest.context.event.EventConfiguration;
import com.zhaofujun.nest.context.event.channel.distribute.DistributeMessageChannel;
import com.zhaofujun.nest.context.event.message.MessageBacklog;
import com.zhaofujun.nest.context.event.message.MessageInfo;
import com.zhaofujun.nest.context.event.resend.MessageResendFactory;
import com.zhaofujun.nest.context.event.resend.MessageResendStore;
import com.zhaofujun.nest.context.model.BaseEntity;
import com.zhaofujun.nest.standard.CustomExceptionable;
import com.zhaofujun.nest.exception.OtherCustomException;
import com.zhaofujun.nest.standard.*;
import com.zhaofujun.nest.utils.EntityCacheUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;


public class UnitOfWork {
    enum EntityOperateEnum {
        create, update, remove
    }

    private Logger logger = LoggerFactory.getLogger(UnitOfWork.class);

    UnitOfWork() {
    }

    private Set<BaseEntity> entities = new HashSet<>();


    public <T extends BaseEntity> T getEntity(Class tClass, Identifier identifier) {
        Entity entity = entities
                .stream()
                .filter(p -> identifier.equals(p.getId()) && tClass.isInstance(p))
                .findFirst()
                .orElse(null);
        return (T) entity;
    }


    public void put(BaseEntity entity) {
        if (entity != null)
            entities.add(entity);
    }

    private void commitEntity() {

        Map<Repository, Map<EntityOperateEnum, List<BaseEntity>>> repositoryMap = toRepositoryMap(entities);
        CacheClient cacheClient = CacheClientFactory.getCacheClient(EntityCacheUtils.getCacheCode());

        repositoryMap.forEach((p, q) -> {
            q.forEach((r, s) -> {
                switch (r) {
                    case create:
                        p.batchInsert(s);
                        //s.parallelStream().forEach(ss -> cacheClient.put(EntityCacheUtils.getCacheKey(ss), ss));
                        break;
                    case update:
                        p.batchUpdate(s);
                        // s.parallelStream().forEach(ss -> cacheClient.put(EntityCacheUtils.getCacheKey(ss), ss));
                        break;
                    case remove:
                        p.batchDelete(s);
                        // s.parallelStream().forEach(ss -> cacheClient.remove(EntityCacheUtils.getCacheKey(ss)));
                }
                s.parallelStream().forEach(ss -> cacheClient.remove(EntityCacheUtils.getCacheKey(ss)));
            });
        });

    }

    private Set<MessageBacklog> messageBacklogs = new HashSet<>();

    public void addMessageBacklog(String eventCode, MessageInfo messageInfo) {
        messageBacklogs.add(new MessageBacklog(eventCode, messageInfo));
    }

    private void commitMessage() {
        messageBacklogs.forEach(p -> {
            ConfigurationManager configurationManager = NestApplication.current().getConfigurationManager();
            EventConfiguration eventConfiguration = configurationManager.getEventConfigurationByEventCode(p.getEventCode());

            DistributeMessageChannel messageChannel = (DistributeMessageChannel) NestApplication.current().getProviderManage().getMessageChannel(eventConfiguration.getMessageChannelCode()); //beanFinder.getInstance(DistributeMessageChannel.class, eventConfiguration.getMessageChannelCode());
            try {
                messageChannel.getMessageProducer().commit(p.getEventCode(), p.getMessageInfo());
            } catch (Exception ex) {
                //投递到消息中间件时发生异常，将有异常的数据存入待发送区域，用于消息补偿
                logger.warn("提交消息时失败，消息将通过补偿器重试，失败原因：" + ex.getMessage(), ex);

                MessageResendStore messageResendStore = MessageResendFactory.create();
                messageResendStore.add(p);
            }
        });
    }

    void commit() {
        ServiceContext serviceContext = ServiceContextManager.get();
        NestApplication.current().beforeCommit(serviceContext);
        try {
            commitEntity();
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
            //清空工作单元中的内容
            entities.clear();
            messageBacklogs.clear();
        }
        NestApplication.current().committed(serviceContext);
    }


    private Map<Repository, Map<EntityOperateEnum, List<BaseEntity>>> toRepositoryMap(Set<BaseEntity> entityMap) {
        Map<Repository, Map<EntityOperateEnum, List<BaseEntity>>> repositoryMap = new HashMap<>();

        entities.stream()
                .filter(p -> !(p.is__new() && p.is__deleted()))//排除又是新增又被删除的
                .filter(p -> !(!p.isChanged() && !p.is__new() && !p.is__deleted())) //排除没有发生改变并且不是新增类型还没有被删除的
                .forEach(p -> {
                    Repository repository = NestApplication.current().getRepositoryManager().create(p.getClass());

                    Map<EntityOperateEnum, List<BaseEntity>> entityOperateEnumListMap = repositoryMap.get(repository);
                    if (!repositoryMap.containsKey(repository)) {
                        entityOperateEnumListMap = new HashMap<>();
                        entityOperateEnumListMap.put(EntityOperateEnum.create, new ArrayList<>());
                        entityOperateEnumListMap.put(EntityOperateEnum.update, new ArrayList<>());
                        entityOperateEnumListMap.put(EntityOperateEnum.remove, new ArrayList<>());
                        repositoryMap.put(repository, entityOperateEnumListMap);
                    }

                    if (p.is__new()) {
                        List<BaseEntity> entityList = entityOperateEnumListMap.get(EntityOperateEnum.create);
                        entityList.add(p);
                    } else if (p.is__deleted()) {
                        List<BaseEntity> entityList = entityOperateEnumListMap.get(EntityOperateEnum.remove);
                        entityList.add(p);
                    } else {
                        List<BaseEntity> entityList = entityOperateEnumListMap.get(EntityOperateEnum.update);
                        entityList.add(p);
                    }


                });


        return repositoryMap;
    }

}
