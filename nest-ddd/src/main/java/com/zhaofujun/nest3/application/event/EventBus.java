package com.zhaofujun.nest3.application.event;


import com.zhaofujun.nest3.model.EventData;
import com.zhaofujun.nest3.model.EventHandler;

public interface EventBus {

    void registerHandler(EventHandler eventHandler);

    default void publish(EventData eventData, long delaySecond) {
        publish(eventData, "?", delaySecond);
    }

    default void publish(EventData eventData) {
        publish(eventData, "?", 0);
    }

    void publish(EventData eventData, String eventSource, long delaySecond);

    void init();
}
