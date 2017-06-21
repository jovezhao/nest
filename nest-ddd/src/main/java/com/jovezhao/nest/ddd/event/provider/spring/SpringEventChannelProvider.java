package com.jovezhao.nest.ddd.event.provider.spring;

import com.jovezhao.nest.ddd.event.ChannelProvider;

/**
 * 基于spring event的消息通道提供者
 * Created by zhaofujun on 2017/6/21.
 */
public class SpringEventChannelProvider implements ChannelProvider {
    @Override
    public void sendMessage(String eventName, Object object) {
        //使用spring来发布事件
    }
}
