package com.zhaofujun.nest.event;

import com.zhaofujun.nest.context.event.channel.MessageChannelProvider;
import com.zhaofujun.nest.context.event.message.MessageInfo;

public interface EventBusListener extends NestEventListener {
    void beforePublish(MessageChannelProvider messageChannelProvider, MessageInfo messageInfo);
    void onReceived(MessageChannelProvider messageChannelProvider, MessageInfo messageInfo);
}
