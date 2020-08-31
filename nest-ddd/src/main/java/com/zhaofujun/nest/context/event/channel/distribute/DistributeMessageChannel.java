package com.zhaofujun.nest.context.event.channel.distribute;

import com.zhaofujun.nest.context.event.channel.MessageChannelProvider;

public abstract class DistributeMessageChannel implements MessageChannelProvider {

    public DistributeMessageChannel() {

    }

    public abstract DistributeMessageProducer getMessageProducer();

    public abstract DistributeMessageConsumer getMessageConsumer();


}
