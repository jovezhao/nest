package com.jovezhao.nest.ddd.event;

import java.io.Serializable;

public interface EventHandler<T extends Serializable> {
    /**
     * 事件名称
     *
     * @return
     */
    String getEventName();

    /**
     * 处理器回调执行
     *
     * @param data
     */
    void handle(T data);

    default String getHandlerName() {
        return this.getClass().getSimpleName();
    }
}
