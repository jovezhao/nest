package com.zhaofujun.nest.context;

import com.zhaofujun.nest.CustomException;
import com.zhaofujun.nest.CustomExceptionable;
import com.zhaofujun.nest.OtherCustomException;
import com.zhaofujun.nest.context.event.message.MessageBacklog;
import com.zhaofujun.nest.context.event.resend.MessageResendFactory;
import com.zhaofujun.nest.context.event.resend.MessageResendStore;
import com.zhaofujun.nest.context.model.Entity;
import com.zhaofujun.nest.core.CacheClient;
import com.zhaofujun.nest.cache.CacheClientFactory;
import com.zhaofujun.nest.configuration.ConfigurationManager;
import com.zhaofujun.nest.configuration.EventConfiguration;
import com.zhaofujun.nest.core.BeanFinder;
import com.zhaofujun.nest.context.event.channel.MessageChannelFactory;
import com.zhaofujun.nest.context.event.message.MessageInfo;
import com.zhaofujun.nest.context.event.channel.distribute.DistributeMessageChannel;
import com.zhaofujun.nest.SystemException;
import com.zhaofujun.nest.core.Identifier;
import com.zhaofujun.nest.core.Repository;
import com.zhaofujun.nest.context.repository.RepositoryFactory;
import com.zhaofujun.nest.utils.EntityCacheUtils;
import com.zhaofujun.nest.utils.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class ContextUnitOfWork {
    private Logger logger = LoggerFactory.getLogger(ContextUnitOfWork.class);

    ContextUnitOfWork() {
    }

    private Map<Entity, EntityOperateEnum> entityMap = new HashMap<>();


    public <T extends Entity> T getEntity(Class<T> tClass, Identifier identifier) {
        Entity entity = entityMap.entrySet()
                .stream()
                .filter(p -> p.getKey().getId().equals(identifier) && tClass.isInstance(p.getKey()))
                .map(p -> p.getKey())
                .findFirst()
                .orElse(null);
        return (T) entity;
    }

    enum EntityOperateEnum {
        create, update, remove
    }

    public void addEntityObject(Entity entity) {
        if (entity != null)
            entityMap.put(entity, EntityOperateEnum.create);
    }

    public void updateEntityObject(Entity entity) {
        if (entity != null)
            entityMap.put(entity, EntityOperateEnum.update);
    }

    public void removeEntityObject(Entity entity) {
        if (entity != null)
            entityMap.put(entity, EntityOperateEnum.remove);
    }

    private void commitEntity() {

        Map<Repository, Map<EntityOperateEnum, List<Entity>>> repositoryMap = toRepositoryMap(entityMap);
        CacheClientFactory cacheClientFactory = new CacheClientFactory(ServiceContext.getCurrent().getApplication().getBeanFinder());
        CacheClient cacheClient = cacheClientFactory.getCacheClient(EntityCacheUtils.getCacheCode());

        repositoryMap.forEach((p, q) -> {
            q.forEach((r, s) -> {
                switch (r) {
                    case create:
                        p.batchInsert(s);
                        s.forEach(ss -> cacheClient.put(EntityCacheUtils.getCacheKey(ss), ss));
                        break;
                    case update:
                        p.batchUpdate(s);
                        s.forEach(ss -> cacheClient.put(EntityCacheUtils.getCacheKey(ss), ss));
                        break;
                    case remove:
                        p.batchDelete(s);
                        s.forEach(ss -> cacheClient.remove(EntityCacheUtils.getCacheKey(ss)));
                }
            });
        });

    }

    private Set<MessageBacklog> messageBacklogs = new HashSet<>();

    public void addMessageBacklog(String eventCode, MessageInfo messageInfo) {
        messageBacklogs.add(new MessageBacklog(eventCode, messageInfo));
    }

    private void commitMessage() {
        BeanFinder beanFinder = ServiceContext.getCurrent().getApplication().getBeanFinder();
        messageBacklogs.forEach(p -> {
            ConfigurationManager configurationManager = ConfigurationManager.getCurrent(beanFinder);
            EventConfiguration eventConfiguration = configurationManager.getEventConfigurationByEventCode(p.getEventCode());
            MessageChannelFactory channelFactory = new MessageChannelFactory(beanFinder);
            DistributeMessageChannel messageChannel = (DistributeMessageChannel) channelFactory.create(eventConfiguration.getMessageChannelCode()); //beanFinder.getInstance(DistributeMessageChannel.class, eventConfiguration.getMessageChannelCode());
            try {
                messageChannel.getMessageProducer().commit(p.getEventCode(), p.getMessageInfo());
            } catch (Exception ex) {
                //投递到消息中间件时发生异常，将有异常的数据存入待发送区域，用于消息补偿
                logger.warn("提交消息时失败，消息将通过补偿器重试，失败原因：" + ex.getMessage(), ex);

                MessageResendFactory resendFactory = new MessageResendFactory(beanFinder);
                MessageResendStore messageResendStore = resendFactory.create();
                messageResendStore.add(p);
            }
        });
    }

    public void commit() {
        ServiceContext serviceContext = ServiceContext.getCurrent();
        serviceContext.getApplication().beforeCommit(serviceContext);
        try {
            commitEntity();
            commitMessage();
        } catch (CustomException ex) {
            throw ex;
        } catch (Exception ex) {

            if (ex instanceof CustomExceptionable) {
                //业务异常
                OtherCustomException customException = new OtherCustomException("发生业务异常", ex);
                throw customException;
            }

            throw new SystemException("提交工作单元时失败", ex);
        } finally {
            //清空工作单元中的内容
            entityMap.clear();
            messageBacklogs.clear();
        }
        serviceContext.getApplication().committed(serviceContext);
    }


    private Map<Repository, Map<EntityOperateEnum, List<Entity>>> toRepositoryMap(Map<Entity, EntityOperateEnum> entityMap) {
        Map<Repository, Map<EntityOperateEnum, List<Entity>>> repositoryMap = new HashMap<>();

        entityMap.entrySet().stream()
                .filter(p -> EntityUtils.isChanged(p.getKey()))
                .forEach(p -> {
                    Repository repository = RepositoryFactory.create(p.getKey().getClass());
                    ;
                    if (!repositoryMap.containsKey(repository))
                        repositoryMap.put(repository, new HashMap<>());

                    Map<EntityOperateEnum, List<Entity>> entityOperateEnumListMap = repositoryMap.get(repository);

                    if (!entityOperateEnumListMap.containsKey(p.getValue()))
                        entityOperateEnumListMap.put(p.getValue(), new ArrayList<>());

                    List<Entity> entityList = entityOperateEnumListMap.get(p.getValue());

                    entityList.add(p.getKey());
                });


        return repositoryMap;
    }

}
