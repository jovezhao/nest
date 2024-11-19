package com.zhaofujun.nest.ddd;

import com.zhaofujun.nest.exception.CustomException;

/**
 * 事件处理器接口。
 *
 * @param <T> 事件数据类型
 */
public interface EventHandler<T> {
    /**
     * 获取事件名称。
     *
     * @return 事件名称
     */
    String getEventName();

    /**
     * 获取事件数据类。
     *
     * @return 事件数据类
     */
    Class<T> getEventDataClass();

    /**
     * 获取消费模式。默认为PUSH。
     *
     * @return 消费模式
     */
    default ConsumeMode getConsumeMode() {
        return ConsumeMode.PUSH;
    }

    /**
     * 处理事件数据。
     *
     * @param eventData 事件数据
     */
    void handle(T eventData);

    /**
     * 处理系统异常。
     *
     * @param context 异常上下文
     * @param ex      异常对象
     */
    default void onSystemException(Object context, Throwable ex) {
        ex.printStackTrace();
    }

    /**
     * 处理自定义异常。
     *
     * @param context 异常上下文
     * @param ex      异常对象
     */
    default void onCustomException(Object context, CustomException ex) {
        ex.printStackTrace();
    }
}
