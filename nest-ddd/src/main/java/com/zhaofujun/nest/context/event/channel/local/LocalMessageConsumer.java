package com.zhaofujun.nest.context.event.channel.local;

import com.zhaofujun.nest.container.BeanFinder;
import com.zhaofujun.nest.context.event.EventHandler;
import com.zhaofujun.nest.context.event.message.MessageInfo;
import com.zhaofujun.nest.context.event.channel.AbstractMessageConsumer;

public class LocalMessageConsumer extends AbstractMessageConsumer {

    private LocalMessageChannel localMessageChannel;

    public LocalMessageConsumer(LocalMessageChannel localMessageChannel, BeanFinder beanFinder) {
        super(beanFinder);
        this.localMessageChannel = localMessageChannel;
    }

    @Override
    public void subscribe(EventHandler eventHandler) {
        EventSource eventSource = localMessageChannel.getEventSource(eventHandler.getEventCode());
        eventSource.addEventListener(new LocalMessageReceivedListener() {
            @Override
            public void onReceived(LocalEvent e) {
                MessageInfo messageInfo = (MessageInfo) e.getArgs()[0];
                onReceivedMessage(messageInfo, eventHandler, eventSource);
            }
        });
    }

    @Override
    protected void onFailed(EventHandler eventHandler, Object context, Exception ex) {

    }

    @Override
    protected void onEnds(EventHandler eventHandler, Object context) {

    }


}
