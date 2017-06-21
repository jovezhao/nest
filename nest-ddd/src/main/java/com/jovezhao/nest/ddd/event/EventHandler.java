package com.jovezhao.nest.ddd.event;

public interface EventHandler<T> {
    /**
     * 事件名称
     * @return
     */
    String getEventName();

    /**
     * 处理器回调执行
     * @param data
     */
    void handle(T data);
}
