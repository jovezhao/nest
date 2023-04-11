package com.zhaofujun.nest3.application.provider;

import com.zhaofujun.nest3.application.event.MessageController;
import com.zhaofujun.nest3.impl.observer.MessageProcessor;
import com.zhaofujun.nest3.model.EventHandler;

public interface MessageChannelProvider extends Provider {


    void commit(String messageGroup, String messageId, String messageInfoString);

    void subscribe(String consumerName, String messageGroup, MessageProcessor messageProcessor);

    void start();

    void close();

}

