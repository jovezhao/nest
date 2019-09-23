package com.zhaofujun.nest.context.event.channel;

public interface MessageChannel {

    MessageProducer getMessageProducer();
    
    MessageConsumer getMessageConsumer();
}

