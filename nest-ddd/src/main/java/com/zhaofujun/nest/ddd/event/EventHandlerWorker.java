package com.zhaofujun.nest.ddd.event;

import java.util.List;
import java.util.Map;
import com.zhaofujun.nest.config.EventHandlerConfig;
import com.zhaofujun.nest.ddd.ConsumeMode;
import com.zhaofujun.nest.ddd.EventHandler;
import com.zhaofujun.nest.manager.EventHandlerManager;
import com.zhaofujun.nest.manager.EventManager;

public class EventHandlerWorker extends Thread {
    private EventChannelProvider provider;
    private EventHandlerConfig config;
    private EventAppService appService;

    public EventHandlerWorker(EventChannelProvider provider, EventHandlerConfig config, EventAppService appService) {
        super("消息处理器-" + provider.getCode());
        this.provider = provider;
        this.config = config;
        this.appService = appService;
    }

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