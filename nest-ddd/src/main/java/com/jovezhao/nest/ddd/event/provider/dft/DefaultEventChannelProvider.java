package com.jovezhao.nest.ddd.event.provider.dft;

import com.jovezhao.nest.ddd.event.ChannelProvider;
import com.jovezhao.nest.ddd.event.EventConsumer;
import com.jovezhao.nest.ddd.event.EventHandler;
import com.jovezhao.nest.ddd.event.EventProducer;
import com.jovezhao.nest.ddd.event.provider.dft.observer.ObserverEventSource;

/**
 * 基于spring event的消息通道提供者
 * Created by zhaofujun on 2017/6/21.
 */
public class DefaultEventChannelProvider extends ChannelProvider {

    private ObserverEventSource eventSource=new ObserverEventSource();


    @Override
    public void init() {

    }

    @Override
    protected EventConsumer createEventConsumer() {
        return new DefaultEventConsumer(eventSource);
    }

    @Override
    public EventProducer createEventProducer() {
        return new DefaultEventProducer(eventSource);
    }


}
