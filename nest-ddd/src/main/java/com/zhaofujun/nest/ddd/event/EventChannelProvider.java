package com.zhaofujun.nest.ddd.event;

import com.zhaofujun.nest.ddd.ConsumeMode;
import com.zhaofujun.nest.provider.Provider;

/**
 * 事件通道提供者接口
 * 每一个事件通道对应一个第三方消息中间件的接入方式
 */
public interface EventChannelProvider extends Provider {

    /**
     * 提交事件数据到事件通道。
     *
     * @param messageGroup 事件组名称
     * @param dataObject   事件数据对象
     */
    void commit(String messageGroup, EventData<?> dataObject);

    /**
     * 获取事件通道消费者。
     *
     * @param mode 消费模式
     * @return 事件通道消费者
     */
    default ChannelConsumer getConsumer(ConsumeMode mode) {
        if (mode == ConsumeMode.PULL)
            return getPullChannelConsumer();
        else
            return getPushChannelConsumer();
    }

    /**
     * 获取拉取模式的事件通道消费者。
     *
     * @return 拉取模式的事件通道消费者
     */
    PullChannelConsumer getPullChannelConsumer();

    /**
     * 获取推送模式的事件通道消费者。
     *
     * @return 推送模式的事件通道消费者
     */
    PushChannelConsumer getPushChannelConsumer();

}
