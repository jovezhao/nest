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
import com.zhaofujun.nest.ddd.event.EventChannelProvider;
import com.zhaofujun.nest.ddd.event.EventHandlerWorker;
import com.zhaofujun.nest.ddd.event.EventSenderWorker;
import com.zhaofujun.nest.inner.DefaultCacheProvider;
import com.zhaofujun.nest.inner.DefaultEventChannelProvider;
import com.zhaofujun.nest.inner.DefaultEventInfoRepository;
import com.zhaofujun.nest.inner.DefaultRepository;
import com.zhaofujun.nest.inner.DefaultLockProvider;
import com.zhaofujun.nest.manager.CacheManager;
import com.zhaofujun.nest.manager.EventHandlerManager;
import com.zhaofujun.nest.manager.ProviderManager;
import com.zhaofujun.nest.manager.RepositoryManager;
import com.zhaofujun.nest.provider.Container;
import com.zhaofujun.nest.provider.Provider;
import com.zhaofujun.nest.utils.EntityUtil;
import com.zhaofujun.nest.utils.MessageUtil;
import com.zhaofujun.nest.utils.cache.CacheClient;

/**
 * NestEngine 类
 * 用于初始化和管理 Nest 框架的核心组件，包括事件处理、仓储管理和缓存管理。
 */
public class NestEngine {

    /**
     * 事件发送线程
     */
    private Thread eventSendThread = null;

    /**
     * 事件处理线程列表
     */
    private List<Thread> eventHandleThread = new ArrayList<>();

    /**
     * 事件发送配置
     */
    private EventSenderConfig sendConfig = new EventSenderConfig();

    /**
     * 事件处理配置
     */
    private EventHandlerConfig handlerConfig = new EventHandlerConfig();

    /**
     * 事件应用服务
     */
    private EventAppService eventAppService;

    /**
     * 设置事件应用服务
     *
     * @param eventAppService 事件应用服务
     */
    public void setEventAppService(EventAppService eventAppService) {
        this.eventAppService = eventAppService;
    }

    /**
     * 初始化 NestEngine
     *
     * @param container 容器，用于加载各种组件
     */
    @SuppressWarnings("unchecked")
    public void init(Container container) {
        // 添加默认锁提供者
        ProviderManager.addProvider(new DefaultLockProvider());
        // 添加默认事件通道提供者
        ProviderManager.addProvider(new DefaultEventChannelProvider());
        // 先加载默认缓存提供者
        ProviderManager.addProvider(new DefaultCacheProvider());
        // 再加载默认缓存
        CacheManager.addCacheItem(NestConst.defaultCacheItem, NestConst.defaultCacheProvider, 5000);
        // 默认仓储依赖于默认缓存
        RepositoryManager.addRepository(new DefaultRepository(), new DefaultEventInfoRepository());
        // 事件
        // EventChannelManager.addEventChannelProvider(new
        // DefaultEventChannelProvider());

        if (container != null) {
            // 加载仓储
            RepositoryManager.addRepository(container.getInstances(Repository.class));
            // 加载事件处理器
            EventHandlerManager.addEventHandler(container.getInstances(EventHandler.class));
            // 加载provider(包括缓存提供者、事件通道提供者)
            ProviderManager.addProvider(container.getInstances(Provider.class));
        }
    }

    /**
     * 启动 NestEngine
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void start() {
        // 处理实体创建事件
        MessageUtil.on(Lifecycle.Entity_New.name(), Entity.class, entity -> {
            if (!EntityLoader.isLoading(entity.getClass(), entity.getId())) {
                ServiceContext currentContext = ServiceContextManager.getCurrentContext();
                if (currentContext != null)
                    currentContext.addEntity(entity);
            }
        });

        // 处理实体更新事件
        MessageUtil.on(Lifecycle.Entity_Updated.name(), (Collection<Entity> entityList) -> {
            // 删除缓存
            CacheClient cacheClient = CacheManager.getCacheClient(NestConst.entityCache);
            entityList.forEach(entity -> {
                cacheClient.remove(EntityUtil.getKey(entity));
            });
        });

        // 处理实体删除事件
        MessageUtil.on(Lifecycle.Entity_Deleted.name(), (Collection<Entity> entityList) -> {
            // 删除缓存
            CacheClient cacheClient = CacheManager.getCacheClient(NestConst.entityCache);
            entityList.forEach(entity -> {
                cacheClient.remove(EntityUtil.getKey(entity));
            });
        });

        // 启动消息发布线程
        eventSendThread = new EventSenderWorker(sendConfig, eventAppService);
        eventSendThread.start();

        // 启动事件处理线程
        ProviderManager.getList(EventChannelProvider.class).forEach(provider -> {
            EventHandlerWorker handlerWorker = new EventHandlerWorker(provider, handlerConfig);
            eventHandleThread.add(handlerWorker);
            handlerWorker.start();
        });
    }

    /**
     * 停止 NestEngine
     */
    public void stop() {
        eventSendThread.interrupt();
        eventHandleThread.forEach(p -> p.interrupt());
    }
}