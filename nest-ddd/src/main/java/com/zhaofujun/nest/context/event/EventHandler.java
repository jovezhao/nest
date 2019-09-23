package com.zhaofujun.nest.context.event;

public interface EventHandler<T extends EventData> {
    String getEventCode();
    Class<T> getEventDataClass();
    void handle(T eventData,EventArgs eventArgs);
}
