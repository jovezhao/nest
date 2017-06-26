package com.jovezhao.nest.ddd.event;

/**
 * 事件处理器
 * @param <T>
 */
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

        String[] arrayName= this.getClass().getName().split("\\.");
        return arrayName[arrayName.length-1];
    }
}
