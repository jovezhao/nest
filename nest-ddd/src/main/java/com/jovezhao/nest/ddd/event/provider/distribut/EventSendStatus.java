package com.jovezhao.nest.ddd.event.provider.distribut;

/**
 * 消息发送状态
 */
public enum EventSendStatus{
    wait,
    commited,
    success,
    fail
}
