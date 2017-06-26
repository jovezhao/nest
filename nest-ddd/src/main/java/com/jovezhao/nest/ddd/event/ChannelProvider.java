package com.jovezhao.nest.ddd.event;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 消息通道提供者
 * Created by zhaofujun on 2017/6/21.
 */
public abstract class ChannelProvider<T extends ProviderConfig> {
    private List<EventConsumer> eventConsumers = new ArrayList<>();

    public abstract void init();

    protected abstract EventConsumer createEventConsumer();

    public abstract EventProducer createEventProducer();

    public void sendMessage(String eventName, Serializable object) {
        EventProducer eventProducer = getEventProducer();
        eventProducer.setProviderConfig(this.providerConfig);
        eventProducer.sendMessage(eventName, object);
    }

    public void subscribe(EventHandler eventHandler) {
        EventConsumer eventConsumer = createEventConsumer();
        eventConsumer.setProviderConfig(this.providerConfig);
        eventConsumer.setEventHandler(eventHandler);
        eventConsumer.start();
        eventConsumers.add(eventConsumer);
    }

    private T providerConfig;

    public void setProviderConfig(T providerConfig) {
        this.providerConfig = providerConfig;
    }

    public T getProviderConfig() {
        return providerConfig;
    }


    public void dispose() {
        eventConsumers.forEach(p -> p.stop());
    }

    private EventProducer eventProducer;

    public EventProducer getEventProducer() {
        if (eventProducer == null)
            eventProducer = createEventProducer();
        return eventProducer;
    }
}

