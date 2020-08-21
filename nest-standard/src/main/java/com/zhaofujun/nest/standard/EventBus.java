package com.zhaofujun.nest.standard;


public interface EventBus {

    void registerHandler(EventHandler eventHandler);

    void publish(EventData eventData);

    void publish(EventData eventData, String eventSource);
}
