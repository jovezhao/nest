package com.jovezhao.nest.ddd.event1;

/**
 * 事件处理器接口
 * Created by Jove on 2016-03-21.
 */
public interface IEventHandler<T> {
    String getEventName();

    void handle(T data);
}
