package com.guoshouxiang.nest.context.event.channel.distribute;

import com.guoshouxiang.nest.context.event.channel.MessageChannel;

public abstract class DistributeMessageChannel implements MessageChannel {

    public abstract DistributeMessageProducer getMessageProducer();

    public abstract DistributeMessageConsumer getMessageConsumer();
}
