package com.jovezhao.nest.ddd.event;

/**
 * 消息通道提供者
 * Created by zhaofujun on 2017/6/21.
 */
public interface ChannelProvider {
    void sendMessage(String eventName,Object object);
}
