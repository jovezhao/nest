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

    /**
     * 销毁通道
     */
    //TODO 销毁消息生产者通道,应该随着IOC容器的销毁而销毁
    public abstract void destruction();
}
