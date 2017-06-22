package com.jovezhao.nest.activemq;

import com.jovezhao.nest.ddd.event.ChannelProvider;
import com.jovezhao.nest.ddd.event.EventConsumer;
import com.jovezhao.nest.ddd.event.EventProducer;

/**
 * Created by Jove on 2016/7/27.
 */

public class ActiveMQChannelProvider extends ChannelProvider<ActiveMQProviderConfig> {


    @Override
    protected EventConsumer createEventConsumer() {
        return new ActiveMQEventConsumer();
    }

    @Override
    public EventProducer createEventProducer() {
        return new ActiveMQEventProducer();
    }
}
