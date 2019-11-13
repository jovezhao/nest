package com.zhaofujun.nest.core;

import com.zhaofujun.nest.context.event.EventArgs;
import com.zhaofujun.nest.context.event.EventData;

public interface EventHandler<T extends EventData> {
    String getEventCode();
    Class<T> getEventDataClass();
    void handle(T eventData, EventArgs eventArgs);
}
