package com.ywkj.nest.ddd;

/**
 * 事件发布
 */
public interface IEventPublish {
    void publish(String eventName, Object data);
}
