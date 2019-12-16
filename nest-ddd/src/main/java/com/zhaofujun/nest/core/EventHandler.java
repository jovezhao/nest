package com.zhaofujun.nest.core;

import com.zhaofujun.nest.context.event.EventArgs;

public interface EventHandler<T extends EventData> {
    String getEventCode();

    Class<T> getEventDataClass();

    void handle(T eventData, EventArgs eventArgs);

    default void onFailed( Object context, Exception ex) {
        ex.printStackTrace();
    }
}
