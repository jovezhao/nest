package com.jovezhao.nest.ddd.event;

/**
 * 服务事件，用于创建一个事件
 * Created by Jove on 2016-03-21.
 */
public class ServiceEvent<T> {

    public static <U> ServiceEvent<U>  createEvent(String eventName, U data) {
        ServiceEvent<U> event = new ServiceEvent<U>();
        event.data = data;
        event.eventName = eventName;
        return event;
    }

    private T data;
    private String eventName;

    public T getData() {
        return data;
    }

    public String getEventName() {
        return eventName;
    }
}
