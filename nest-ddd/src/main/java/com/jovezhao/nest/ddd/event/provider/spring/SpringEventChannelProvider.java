package com.jovezhao.nest.ddd.event.provider.spring;

import com.jovezhao.nest.ddd.event.ChannelProvider;
import com.jovezhao.nest.ddd.event.EventConsumer;
import com.jovezhao.nest.ddd.event.EventProducer;

/**
 * 基于spring event的消息通道提供者
 * Created by zhaofujun on 2017/6/21.
 */
public class SpringEventChannelProvider extends ChannelProvider {

    @Override
    protected EventConsumer getEventConsumer() {
        return null;
    }

    @Override
    public EventProducer getEventProducer() {
        return null;
    }
}
