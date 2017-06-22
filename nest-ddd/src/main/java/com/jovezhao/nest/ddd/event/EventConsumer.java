package com.jovezhao.nest.ddd.event;

/**
 * 事件消费者,消费者使用一个独立线程来监听，并且提供停止监听的方法
 */
public abstract class EventConsumer<T extends ProviderConfig> {
    private T providerConfig;

    public T getProviderConfig() {
        return providerConfig;
    }


    public void setProviderConfig(T providerConfig) {
        this.providerConfig = providerConfig;
    }


    private EventHandler eventHandler;


    public void setEventHandler(EventHandler eventHandler) {
        this.eventHandler = eventHandler;
    }

    public EventHandler getEventHandler() {
        return eventHandler;
    }

    protected void start() {

    }




    public void stop() {
    }

}
