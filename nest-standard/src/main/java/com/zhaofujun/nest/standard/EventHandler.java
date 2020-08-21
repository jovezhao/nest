package com.zhaofujun.nest.standard;


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
