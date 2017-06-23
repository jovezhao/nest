package com.jovezhao.nest.ddd.event;

import java.io.Serializable;

public interface EventHandler<T> {
    /**
     * 事件名称
     *
     * @return
     */
    String getEventName();
    Class<T> getTClass();

    /**
     * 处理器回调执行
     *
     * @param data
     */
    void handle(T data) throws Exception;

    default String getHandlerName() {
        return this.getClass().getSimpleName();
    }
}
