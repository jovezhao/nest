package com.zhaofujun.nest.ddd;

import com.zhaofujun.nest.exception.CustomException;

public interface EventHandler<T> {
    String getEventName();

    Class<T> getEventDataClass();

    default ConsumeMode getConsumeMode() {
        return ConsumeMode.PUSH;
    }

    void handle(T eventData);

    default void onSystemException(Object context, Throwable ex) {
        ex.printStackTrace();
    }

    default void onCustomException(Object context, CustomException ex) {
        ex.printStackTrace();
    }
}
