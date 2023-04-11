package com.zhaofujun.nest3.impl.observer;

import com.zhaofujun.nest3.application.provider.MessageChannelProvider;

public class DefaultMessageChannelProvider implements MessageChannelProvider {
    @Override
    public void commit(String messageGroup, String messageId, String messageInfoString) {
        EventSource eventSource = EventSource.getEventSource(messageGroup);
        eventSource.send(messageInfoString);
    }

    @Override
    public void subscribe(String consumerName, String messageGroup, MessageProcessor messageProcessor) {
        EventSource eventSource = EventSource.getEventSource(messageGroup);
        eventSource.addEventListener(e -> {
            String text = e.getArgs()[0];
            messageProcessor.onReceivedMessage(text, null);
        });

    }

    @Override
    public void start() {
    }

    @Override
    public void close() {
    }

    @Override
    public String getCode() {
        return "DefaultMessageChannelProvider";
    }
}
