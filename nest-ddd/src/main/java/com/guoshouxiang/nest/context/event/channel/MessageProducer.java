package com.guoshouxiang.nest.context.event.channel;

import com.guoshouxiang.nest.context.event.message.MessageInfo;

public interface    MessageProducer {
    void send(String messageGroup,MessageInfo messageInfo);
}
