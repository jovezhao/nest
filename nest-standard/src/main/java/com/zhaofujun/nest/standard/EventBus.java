package com.zhaofujun.nest.standard;


public interface EventBus {

    void registerHandler(EventHandler eventHandler);

    default void publish(EventData eventData, long delay) {
        publish(eventData, "?", delay);
    }

    default void publish(EventData eventData) {
        publish(eventData, "?", 0);
    }

    void publish(EventData eventData, String eventSource, long delay);
}
