package com.zhaofujun.nest.core;


public interface EventBus {
    void autoRegister();

    void registerHandler(EventHandler eventHandler);

    void publish(EventData eventData);

    public void publish(EventData eventData, String eventSource);
}
