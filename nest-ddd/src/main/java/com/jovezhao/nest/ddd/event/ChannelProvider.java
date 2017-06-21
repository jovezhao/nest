package com.jovezhao.nest.ddd.event;

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
    List<EventConsumer> eventConsumers = new ArrayList<>();

    protected abstract EventConsumer getEventConsumer();

    public abstract EventProducer getEventProducer();

    public void sendMessage(String eventName, Serializable object) {
        EventProducer eventProducer = getEventProducer();
        eventProducer.setProviderConfig(providerConfig);
        eventProducer.sendMessage(eventName, object);
    }

    public void subscribe(EventHandler eventHandler) {
        EventConsumer eventConsumer = getEventConsumer();
        eventConsumer.setEventHandler(eventHandler);
        eventConsumer.setProviderConfig(providerConfig);

        Thread eventThread = new Thread(eventConsumer);
        eventThread.setName(eventHandler.getEventName());
        eventThread.start();

        eventConsumers.add(eventConsumer);

    }

    private T providerConfig;

    public void setProviderConfig(T providerConfig) {
        this.providerConfig = providerConfig;
    }

    public T getProviderConfig() {
        return providerConfig;
    }


    public void displose() {
        eventConsumers.forEach(p -> p.stop());
    }
}

