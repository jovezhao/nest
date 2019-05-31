package com.guoshouxiang.nest.context.event.channel;

public interface MessageChannel {

    MessageProducer getMessageProducer();
    
    MessageConsumer getMessageConsumer();
}

