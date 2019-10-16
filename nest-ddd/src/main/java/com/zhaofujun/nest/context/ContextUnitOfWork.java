package com.zhaofujun.nest.context;

import com.zhaofujun.nest.cache.CacheClient;
import com.zhaofujun.nest.cache.CacheClientFactory;
import com.zhaofujun.nest.configuration.ConfigurationManager;
import com.zhaofujun.nest.configuration.EventConfiguration;
import com.zhaofujun.nest.container.BeanFinder;
import com.zhaofujun.nest.context.event.message.MessageInfo;
import com.zhaofujun.nest.context.event.channel.distribute.DistributeMessageChannel;
import com.zhaofujun.nest.SystemException;
import com.zhaofujun.nest.context.model.BaseEntity;
import com.zhaofujun.nest.context.model.Identifier;
import com.zhaofujun.nest.context.repository.Repository;
import com.zhaofujun.nest.context.repository.RepositoryFactory;
import com.zhaofujun.nest.utils.EntityCacheUtils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ContextUnitOfWork {

    ContextUnitOfWork() {
    }

    private Map<BaseEntity, EntityOperateEnum> entityMap = new HashMap<>();

    public <T extends BaseEntity> T getEntity(Class<T> tClass, Identifier identifier) {
        BaseEntity baseEntity = entityMap.entrySet()
                .stream()
                .filter(p -> p.getKey().getId().equals(identifier) && tClass.isInstance(p.getKey()))
                .map(p -> p.getKey())
                .findFirst()
                .orElse(null);
        return (T) baseEntity;
    }

    enum EntityOperateEnum {
        save, remove
    }

    public void addEntityObject(BaseEntity baseEntity) {
        entityMap.put(baseEntity, EntityOperateEnum.save);
    }

    public void removeEntityObject(BaseEntity baseEntity) {
        entityMap.put(baseEntity, EntityOperateEnum.remove);
    }

    private void commitEntity() {
        entityMap.forEach((p, q) -> {
            Repository repository = RepositoryFactory.create(p.getClass());
            switch (q) {
                case save:
                    repository.save(p);
                    break;
                case remove:
                    repository.remove(p);
            }
        });
    }

    private Set<MessageBacklog> messageBacklogs = new HashSet<>();

    public void addMessageBacklog(String eventCode, MessageInfo messageInfo) {
        messageBacklogs.add(new MessageBacklog(eventCode, messageInfo));
    }

    private void commitMessage() {
        BeanFinder beanFinder = ServiceContext.getCurrent().getApplication().getBeanFinder();
        messageBacklogs.forEach(p -> {
            ConfigurationManager configurationManager = new ConfigurationManager(beanFinder);
            EventConfiguration eventConfiguration = configurationManager.getEventConfigurationByCode(p.eventCode);
            DistributeMessageChannel messageChannel = beanFinder.getInstance(DistributeMessageChannel.class, eventConfiguration.getMessageChannelCode());
            messageChannel.getMessageProducer().commit(p.getEventCode(), p.getMessageInfo());
        });
    }

    public void commit() {
        ServiceContext serviceContext = ServiceContext.getCurrent();
        serviceContext.getApplication().beforeCommit(serviceContext);
        try {
            commitEntity();
            commitMessage();
            cacheCommit();
        } catch (Exception ex) {
            removeCache();
            throw new SystemException(("提交工作单元时失败"), ex);
        } finally {
            //清空工作单元中的内容
            entityMap.clear();
            messageBacklogs.clear();
        }
        serviceContext.getApplication().committed(serviceContext);
    }


    private void cacheCommit() {

        CacheClientFactory cacheClientFactory = new CacheClientFactory(ServiceContext.getCurrent().getApplication().getBeanFinder());
        CacheClient cacheClient = cacheClientFactory.getCacheClient(EntityCacheUtils.getCacheCode());
        entityMap.forEach((p, q) -> {
            switch (q) {
                case save:
                    cacheClient.put(EntityCacheUtils.getCacheKey(p), p);
                    break;
                case remove:
                    cacheClient.remove(EntityCacheUtils.getCacheKey(p));
            }
        });
    }

    private void removeCache() {
        CacheClientFactory cacheClientFactory = new CacheClientFactory(ServiceContext.getCurrent().getApplication().getBeanFinder());
        CacheClient cacheClient = cacheClientFactory.getCacheClient(EntityCacheUtils.getCacheCode());
        entityMap.forEach((p, q) -> {
            cacheClient.remove(EntityCacheUtils.getCacheKey(p));

        });
    }


    class MessageBacklog {
        private String eventCode;
        private MessageInfo messageInfo;

        public MessageBacklog(String eventCode, MessageInfo messageInfo) {
            this.eventCode = eventCode;
            this.messageInfo = messageInfo;
        }

        public String getEventCode() {
            return eventCode;
        }

        public void setEventCode(String eventCode) {
            this.eventCode = eventCode;
        }

        public MessageInfo getMessageInfo() {
            return messageInfo;
        }

        public void setMessageInfo(MessageInfo messageInfo) {
            this.messageInfo = messageInfo;
        }
    }
}
