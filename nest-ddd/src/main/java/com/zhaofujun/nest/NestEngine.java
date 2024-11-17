package com.zhaofujun.nest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.zhaofujun.nest.config.EventHandlerConfig;
import com.zhaofujun.nest.config.EventSenderConfig;
import com.zhaofujun.nest.ddd.Entity;
import com.zhaofujun.nest.ddd.EventHandler;
import com.zhaofujun.nest.ddd.Repository;
import com.zhaofujun.nest.ddd.context.EntityLoader;
import com.zhaofujun.nest.ddd.context.ServiceContext;
import com.zhaofujun.nest.ddd.context.ServiceContextManager;
import com.zhaofujun.nest.ddd.event.EventAppService;
import com.zhaofujun.nest.ddd.event.EventHandlerWorker;
import com.zhaofujun.nest.ddd.event.EventSenderWorker;
import com.zhaofujun.nest.inner.DefaultCacheProvider;
import com.zhaofujun.nest.inner.DefaultEventChannelProvider;
import com.zhaofujun.nest.inner.DefaultEventInfoRepostory;
import com.zhaofujun.nest.inner.DefaultRepository;
import com.zhaofujun.nest.manager.CacheManager;
import com.zhaofujun.nest.manager.EventChannelManager;
import com.zhaofujun.nest.manager.EventHandlerManager;
import com.zhaofujun.nest.manager.ProviderManager;
import com.zhaofujun.nest.manager.RepositoryManager;
import com.zhaofujun.nest.provider.Container;
import com.zhaofujun.nest.provider.Provider;
import com.zhaofujun.nest.utils.EntityUtil;
import com.zhaofujun.nest.utils.MessageUtil;
import com.zhaofujun.nest.utils.cache.CacheClient;

public class NestEngine {

    private Thread eventSendThread = null;
    private List<Thread> eventHandleThread = new ArrayList<>();
    private EventSenderConfig sendConfig = new EventSenderConfig();
    private EventHandlerConfig handlerConfig = new EventHandlerConfig();
    private EventAppService eventAppService;

    public void setEventAppService(EventAppService eventAppService) {
        this.eventAppService = eventAppService;
    }

    public void init(Container container) {
        // 先加载默认缓存提供者
        ProviderManager.addProvider(new DefaultCacheProvider());
        //再加载默认缓存
        CacheManager.addCacheItem(NestConst.defaultCacheItem, NestConst.defaultCacheProvider, 5000);
        //默认仓储依赖于默认缓存
        RepositoryManager.addRepository(new DefaultRepository(), new DefaultEventInfoRepostory());
        //事件
        EventChannelManager.addEventChannelProvider(new DefaultEventChannelProvider());
        

        if (container != null) {
            // 加载仓储
            RepositoryManager.addRepository(container.getInstances(Repository.class));
            // 加载事件处理器
            EventHandlerManager.addEventHandler(container.getInstances(EventHandler.class));
            // 加载provider(包括缓存提供者、事件通道提供者)
            ProviderManager.addProvider(container.getInstances(Provider.class));
        }
    }

    @SuppressWarnings("rawtypes")
    public void start() {
        // 处理消息事件
        MessageUtil.on(Lifecycle.Entity_New.name(), Entity.class, entity -> {
            if (!EntityLoader.isLoading(entity.getClass(), entity.getId())) {
                ServiceContext currentContext = ServiceContextManager.getCurrentContext();
                if (currentContext != null)
                    currentContext.addEntity(entity);
            }
        });
        MessageUtil.on(Lifecycle.Entity_Updated.name(), (Collection<Entity> entitys) -> {
            // 删除缓存
            CacheClient cacheClient = CacheManager.getCacheClient(NestConst.entityCache);
            entitys.forEach(entity -> {
                cacheClient.remove(EntityUtil.getKey(entity));
            });
        });

        MessageUtil.on(Lifecycle.Entity_Deleted.name(), (Collection<Entity> entitys) -> {
            // 删除缓存
            CacheClient cacheClient = CacheManager.getCacheClient(NestConst.entityCache);
            entitys.forEach(entity -> {
                cacheClient.remove(EntityUtil.getKey(entity));
            });
        });

        // 启动消息发布线程
        eventSendThread = new EventSenderWorker(sendConfig, eventAppService);
        eventSendThread.start();


        // 启动事件处理线程
        EventChannelManager.getChannelProviders().forEach(provider -> {
            EventHandlerWorker handlerWorker = new EventHandlerWorker(provider, handlerConfig);
            eventHandleThread.add(handlerWorker);
            handlerWorker.start();
        });
    }

    public void stop() {
        eventSendThread.interrupt();
        eventHandleThread.forEach(p -> p.interrupt());
    }
}
