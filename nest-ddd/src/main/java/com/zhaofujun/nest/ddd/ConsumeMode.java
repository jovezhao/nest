package com.zhaofujun.nest.ddd;

/**
 * 消费模式枚举类
 * 用于定义数据消费的两种模式：推模式和拉模式
 */
public enum ConsumeMode {
    /**
     * 推模式
     * 数据由生产者主动发送给消费者
     */
    PUSH,
    /**
     * 拉模式
     * 消费者主动从生产者那里获取数据
     */
    PULL
}