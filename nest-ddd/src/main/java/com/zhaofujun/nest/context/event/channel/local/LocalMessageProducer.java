package com.zhaofujun.nest.context.event.channel.local;

import com.zhaofujun.nest.context.event.channel.distribute.DistributeMessageProducer;
import com.zhaofujun.nest.context.event.message.MessageConverterFactory;
import com.zhaofujun.nest.context.event.message.MessageInfo;

public class LocalMessageProducer extends DistributeMessageProducer {


    @Override
    public void commit(String messageGroup, String messageInfoString) {
        EventSource eventSource = EventSource.getEventSource(messageGroup);
        eventSource.send(messageInfoString);
    }
}
