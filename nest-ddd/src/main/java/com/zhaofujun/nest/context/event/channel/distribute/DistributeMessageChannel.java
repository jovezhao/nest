package com.zhaofujun.nest.context.event.channel.distribute;

import com.zhaofujun.nest.context.event.channel.MessageChannel;

public abstract class DistributeMessageChannel implements MessageChannel {

    public DistributeMessageChannel() {

    }

    public abstract DistributeMessageProducer getMessageProducer();

    public abstract DistributeMessageConsumer getMessageConsumer();

     public abstract void onStart();
    public abstract void onClose();
}
