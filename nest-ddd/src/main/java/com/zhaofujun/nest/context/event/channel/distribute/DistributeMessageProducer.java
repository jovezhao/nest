package com.zhaofujun.nest.context.event.channel.distribute;

import com.zhaofujun.nest.context.appservice.ServiceContextManager;
import com.zhaofujun.nest.context.event.message.MessageConverter;
import com.zhaofujun.nest.context.event.message.MessageConverterFactory;
import com.zhaofujun.nest.context.event.message.MessageInfo;
import com.zhaofujun.nest.context.event.channel.MessageProducer;

public abstract class DistributeMessageProducer implements MessageProducer {
    private MessageConverter messageConverter;

    public DistributeMessageProducer() {
        this.messageConverter =  MessageConverterFactory.create();
    }

    public void send(String messageGroup, MessageInfo messageInfo) {

        ServiceContextManager.get()
                .addMessageBacklog(messageGroup, messageInfo);
    }

    public abstract void commit(String messageGroup, MessageInfo messageInfo);

    @Override
    public MessageConverter getMessageConverter() {
        return this.messageConverter;
    }
}
