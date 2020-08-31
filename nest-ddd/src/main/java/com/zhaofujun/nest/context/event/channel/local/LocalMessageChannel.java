package com.zhaofujun.nest.context.event.channel.local;


import com.zhaofujun.nest.context.event.channel.distribute.DistributeMessageChannel;
import com.zhaofujun.nest.context.event.channel.distribute.DistributeMessageConsumer;
import com.zhaofujun.nest.context.event.channel.distribute.DistributeMessageProducer;

public class LocalMessageChannel extends DistributeMessageChannel {

    public static final String CHANNEL_CODE = "LocalMessageChannel";

    @Override
    public String getCode() {
        return CHANNEL_CODE;
    }

    @Override
    public DistributeMessageProducer getMessageProducer() {
        return new LocalMessageProducer();
    }

    @Override
    public DistributeMessageConsumer getMessageConsumer() {
        return new LocalMessageConsumer();
    }

    @Override
    public void start() {
    }

    @Override
    public void close() {
        EventSource.removeAll();
    }
}

