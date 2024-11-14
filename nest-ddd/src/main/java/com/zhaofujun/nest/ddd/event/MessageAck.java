package com.zhaofujun.nest.ddd.event;

public enum MessageAck {

    /**
     * 反馈消息处理成功
     */
    SUCCESS,

    /**
     * 拒绝消息，消息将回返回mq 下次再重新消费
     */
    REFUSE;
}