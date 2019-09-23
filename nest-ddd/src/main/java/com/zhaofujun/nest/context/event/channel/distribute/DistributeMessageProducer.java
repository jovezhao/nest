package com.zhaofujun.nest.context.event.channel.distribute;

import com.zhaofujun.nest.context.ServiceContext;
import com.zhaofujun.nest.context.event.message.MessageInfo;
import com.zhaofujun.nest.context.event.channel.MessageProducer;

public abstract class DistributeMessageProducer implements MessageProducer {
    public void send(String messageGroup, MessageInfo messageInfo) {

        ServiceContext.getCurrent()
                .getContextUnitOfWork()
                .addMessageBacklog(messageGroup, messageInfo);
    }

    public abstract void commit(String messageGroup, MessageInfo messageInfo);
}
