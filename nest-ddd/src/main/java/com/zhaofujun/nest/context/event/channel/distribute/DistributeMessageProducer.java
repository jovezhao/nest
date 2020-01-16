package com.zhaofujun.nest.context.event.channel.distribute;

import com.zhaofujun.nest.context.ServiceContextManager;
import com.zhaofujun.nest.context.event.message.MessageConverter;
import com.zhaofujun.nest.context.event.message.MessageConverterFactory;
import com.zhaofujun.nest.context.event.message.MessageInfo;
import com.zhaofujun.nest.context.event.channel.MessageProducer;
import com.zhaofujun.nest.core.BeanFinder;

public abstract class DistributeMessageProducer implements MessageProducer {
    private BeanFinder beanFinder;
    private MessageConverter messageConverter;

    public DistributeMessageProducer(BeanFinder beanFinder) {
        this.messageConverter = new MessageConverterFactory(beanFinder).create();
        this.beanFinder = beanFinder;
    }

    public BeanFinder getBeanFinder() {
        return beanFinder;
    }

    public void send(String messageGroup, MessageInfo messageInfo) {

        ServiceContextManager.getCurrent()
                .getContextUnitOfWork()
                .addMessageBacklog(messageGroup, messageInfo);
    }

    public abstract void commit(String messageGroup, MessageInfo messageInfo);

    @Override
    public MessageConverter getMessageConverter() {
        return this.messageConverter;
    }
}
