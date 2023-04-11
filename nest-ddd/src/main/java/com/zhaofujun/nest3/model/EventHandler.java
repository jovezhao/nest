package com.zhaofujun.nest3.model;


import com.zhaofujun.nest3.CustomException;
import com.zhaofujun.nest3.SystemException;

public interface EventHandler<T extends EventData> {
    String getEventCode();

    Class<T> getEventDataClass();

    void handle(T eventData, EventArgs eventArgs);

    default void onSystemException(SystemException ex) {
        ex.printStackTrace();
    }

    default void onCustomException(CustomException ex) {
        ex.printStackTrace();
    }
}
