package com.zhaofujun.nest.core;


import com.zhaofujun.nest.context.event.EventData;


public interface EventBus{
    void autoRegister();
    void registerHandler(EventHandler eventHandler);
    void publish(EventData eventData);
}
