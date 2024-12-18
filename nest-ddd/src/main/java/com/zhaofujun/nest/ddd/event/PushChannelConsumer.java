package com.zhaofujun.nest.ddd.event;

import java.util.Collection;
import com.zhaofujun.nest.ddd.EventHandler;

public abstract class PushChannelConsumer implements ChannelConsumer {

    private EventAppService appService;

    @Override
    public void setEventAppService(EventAppService appService) {
        this.appService = appService;
    }
    /**
     * 并且为每eventHandler创建一个EventConsumer，
     * 
     * 
     * @param eventName
     * @param eventHandlers
     */
    public void register(String eventName, Collection<EventHandler> eventHandlers) {
        for (EventHandler<?> handler : eventHandlers) {
            MessageProcessor<?> eventConsumer = new MessageProcessor<>(handler,appService);
            subscribe(eventConsumer);
        }
    }

    /**
     * 订阅 consumer，在回调中执行 consumer.invoke()
     * 根据 invoke 返回值处理消息确认方式
     * 
     * @param consumer
     */
    protected abstract <T> void subscribe(MessageProcessor<T> consumer);
}