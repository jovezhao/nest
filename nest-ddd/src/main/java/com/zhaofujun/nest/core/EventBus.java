package com.zhaofujun.nest.core;


public interface EventBus{
    void autoRegister();
    void registerHandler(EventHandler eventHandler);
    void publish(EventData eventData);
}
