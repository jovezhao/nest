package com.jovezhao.nest.ddd.event1;

/**
 * 事件发布
 */
public interface IEventPublish {
    void publish(String eventName, Object data);
}
