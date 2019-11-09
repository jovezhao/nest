package com.zhaofujun.nest.context.event.channel;

public interface MessageChannel {

     String getMessageChannelCode();

    MessageProducer getMessageProducer();
    
    MessageConsumer getMessageConsumer();
}

