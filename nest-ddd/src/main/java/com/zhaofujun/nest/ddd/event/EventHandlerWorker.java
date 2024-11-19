package com.zhaofujun.nest.ddd.event;

import java.util.List;
import java.util.Map;
import com.zhaofujun.nest.config.EventHandlerConfig;
import com.zhaofujun.nest.ddd.ConsumeMode;
import com.zhaofujun.nest.ddd.EventHandler;
import com.zhaofujun.nest.manager.EventHandlerManager;
import com.zhaofujun.nest.manager.EventManager;

/**
 * 事件处理器工作者线程，负责注册事件处理器并处理事件。
 */
public class EventHandlerWorker extends Thread {
    /**
     * 事件通道提供者。
     */
    private EventChannelProvider provider;

    /**
     * 事件处理器配置。
     */
    private EventHandlerConfig config;

    /**
     * 事件应用服务。
     */
    private EventAppService appService;

    /**
     * 构造函数。
     *
     * @param provider 事件通道提供者
     * @param config   事件处理器配置
     * @param appService 事件应用服务
     */
    public EventHandlerWorker(EventChannelProvider provider, EventHandlerConfig config, EventAppService appService) {
        super("消息处理器-" + provider.getCode());
        this.provider = provider;
        this.config = config;
        this.appService = appService;
    }

    /**
     * 运行方法，负责注册事件处理器并处理事件。
     */
    @Override
    public void run() {

        // 找到所有事件
        List<String> eventNames = EventManager.getEventNames(provider.getCode());

        eventNames.forEach(eventName -> {
            // 找到事件下所有 eventHandler
            Map<ConsumeMode, List<EventHandler>> eventHandlers = EventHandlerManager.getEventHandlers(eventName);
            eventHandlers.forEach((p, q) -> {
                ChannelConsumer consumer = provider.getConsumer(p);
                consumer.setEventAppService(appService);
                consumer.register(eventName, q);
            });
        });

    }
}
