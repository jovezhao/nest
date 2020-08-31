package com.zhaofujun.nest.context.event.channel;

import com.zhaofujun.nest.provider.Provider;

public interface MessageChannelProvider extends Provider {

    MessageProducer getMessageProducer();

    MessageConsumer getMessageConsumer();

    void start();

    void close();
}

