package com.zhaofujun.nest.context.event.channel;

import com.zhaofujun.nest.context.event.message.MessageInfo;

public interface MessageProducer {
    void send(String messageGroup, MessageInfo messageInfo);
}
