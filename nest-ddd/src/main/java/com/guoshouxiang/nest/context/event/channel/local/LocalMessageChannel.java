package com.guoshouxiang.nest.context.event.channel.local;


import com.guoshouxiang.nest.container.BeanFinder;
import com.guoshouxiang.nest.context.event.EventHandler;
import com.guoshouxiang.nest.context.event.message.MessageInfo;
import com.guoshouxiang.nest.context.event.channel.MessageChannel;
import com.guoshouxiang.nest.context.event.channel.MessageConsumer;
import com.guoshouxiang.nest.context.event.channel.MessageProducer;

import java.util.HashMap;
import java.util.Map;

public class LocalMessageChannel implements MessageChannel, MessageProducer, MessageConsumer {

    private MessageConsumer messageConsumer;

    public LocalMessageChannel(BeanFinder beanFinder) {
        this.messageConsumer = new LocalMessageConsumer(this, beanFinder);
    }


    private static Map<String, EventSource> eventSourceMap = new HashMap<>();

    public EventSource getEventSource(String messageGroup) {
        EventSource eventSource = eventSourceMap.get(messageGroup);
        if (eventSource == null) {
            eventSource = new EventSource(this);
            eventSourceMap.put(messageGroup, eventSource);
        }
        return eventSource;
    }

    @Override
    public MessageProducer getMessageProducer() {
        return this;
    }

    @Override
    public MessageConsumer getMessageConsumer() {
        return this;
    }

    @Override
    public void send(String messageGroup, MessageInfo messageInfo) {
        EventSource eventSource = getEventSource(messageGroup);
        eventSource.send(messageInfo);
    }


    @Override
    public void subscribe(EventHandler eventHandler) {
        this.messageConsumer.subscribe(eventHandler);
    }
}

