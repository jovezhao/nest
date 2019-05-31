package com.guoshouxiang.nest.context.event.channel.distribute;

import com.guoshouxiang.nest.context.ServiceContext;
import com.guoshouxiang.nest.context.event.message.MessageInfo;
import com.guoshouxiang.nest.context.event.channel.MessageProducer;

public abstract class DistributeMessageProducer implements MessageProducer {
    public void send(String messageGroup, MessageInfo messageInfo) {

        ServiceContext.getCurrent()
                .getContextUnitOfWork()
                .addMessageBacklog(messageGroup, messageInfo);
    }

    public abstract void commit(String messageGroup, MessageInfo messageInfo);
}
