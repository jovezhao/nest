package com.zhaofujun.nest.core;

import com.zhaofujun.nest.CustomException;
import com.zhaofujun.nest.SystemException;
import com.zhaofujun.nest.context.event.EventArgs;

public interface EventHandler<T extends EventData> {
    String getEventCode();

    Class<T> getEventDataClass();

    void handle(T eventData, EventArgs eventArgs);

    default void onSystemException( Object context, SystemException ex) {
        ex.printStackTrace();
    }
    default void onCustomException( Object context, CustomException ex) {
        ex.printStackTrace();
    }
}
