package com.zhaofujun.nest.context.event.channel.local;

import com.zhaofujun.nest.context.event.channel.distribute.DistributeMessageProducer;

public class LocalMessageProducer extends DistributeMessageProducer {


    @Override
    public void commit(String messageGroup, String messageId, String messageInfoString) {
        EventSource eventSource = EventSource.getEventSource(messageGroup);
        eventSource.send(messageInfoString);
    }
}
