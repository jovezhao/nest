package com.zhaofujun.nest.context.event.channel.local;

import com.zhaofujun.nest.context.event.channel.distribute.DistributeMessageConsumer;
import com.zhaofujun.nest.context.event.message.MessageConverterFactory;
import com.zhaofujun.nest.context.event.message.MessageInfo;
import com.zhaofujun.nest.standard.EventHandler;

public class LocalMessageConsumer extends DistributeMessageConsumer {


    @Override
    public void subscribe(EventHandler eventHandler) {
        EventSource eventSource = EventSource.getEventSource(eventHandler.getEventCode());
        eventSource.addEventListener(new LocalMessageReceivedListener() {
            @Override
            public void onReceived(LocalEvent e) {
                String messageText = e.getArgs()[0];
                onReceivedMessage(messageText, eventHandler, eventSource);
            }
        });
    }

    @Override
    protected void onFailed(EventHandler eventHandler, Object context, Exception ex) {

    }

    @Override
    protected void onEnds(EventHandler eventHandler, Object context) {

    }


    @Override
    public void stop() {

    }
}
