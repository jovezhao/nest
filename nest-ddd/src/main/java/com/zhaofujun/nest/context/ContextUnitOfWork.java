package com.zhaofujun.nest.context;

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

import java.util.*;

public class ContextUnitOfWork {

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
        entityMap.put(entity, EntityOperateEnum.create);
    }

    public void updateEntityObject(Entity entity) {
        entityMap.put(entity, EntityOperateEnum.update);
    }

    public void removeEntityObject(Entity entity) {
        entityMap.put(entity, EntityOperateEnum.remove);
    }

    private void commitEntity() {

        Map<Repository, Map<EntityOperateEnum, List<Entity>>> repositoryMap = toRepositoryMap(entityMap);

        repositoryMap.forEach((p, q) -> {
            q.forEach((r, s) -> {
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

//
//        entityMap.forEach((p, q) -> {
//            Repository repository = RepositoryFactory.create(p.getClass());
//            switch (q) {
//                case create:
//                    repository.insert(p);
//                    break;
//                case update:
//                    repository.update(p);
//                    break;
//                case remove:
//                    repository.remove(p);
//            }
//        });
    }

    private Set<MessageBacklog> messageBacklogs = new HashSet<>();

    public void addMessageBacklog(String eventCode, MessageInfo messageInfo) {
        messageBacklogs.add(new MessageBacklog(eventCode, messageInfo));
    }

    private void commitMessage() {
        BeanFinder beanFinder = ServiceContext.getCurrent().getApplication().getBeanFinder();
        messageBacklogs.forEach(p -> {
            ConfigurationManager configurationManager = ConfigurationManager.getCurrent(beanFinder);
            EventConfiguration eventConfiguration = configurationManager.getEventConfigurationByEventCode(p.eventCode);
            MessageChannelFactory channelFactory = new MessageChannelFactory(beanFinder);
            DistributeMessageChannel messageChannel = (DistributeMessageChannel) channelFactory.create(eventConfiguration.getMessageChannelCode()); //beanFinder.getInstance(DistributeMessageChannel.class, eventConfiguration.getMessageChannelCode());
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
            ex.printStackTrace();
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
                case create:
                    cacheClient.put(EntityCacheUtils.getCacheKey(p), p);
                    break;
                case update:
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


        public MessageInfo getMessageInfo() {
            return messageInfo;
        }

    }


    private Map<Repository, Map<EntityOperateEnum, List<Entity>>> toRepositoryMap(Map<Entity, EntityOperateEnum> entityMap) {
        Map<Repository, Map<EntityOperateEnum, List<Entity>>> repositoryMap = new HashMap<>();

        entityMap.entrySet().stream().forEach(p -> {
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
