package com.jovezhao.nest.ddd.event.provider.distribut;

import com.jovezhao.nest.ddd.event.ChannelProvider;
import com.jovezhao.nest.ddd.event.EventChannelManager;

/**
 * 服务事件，用于创建一个事件
 * Created by Jove on 2016-03-21.
 */
public class EventData<T> {

    public static <U> EventData<U> createEvent(String eventName, U data) {
        EventData<U> event = new EventData<U>();
        event.data = data;
        event.eventName = eventName;
        return event;
    }

    private T data;
    private String eventName;

    public T getData() {
        return data;
    }

    public String getEventName() {
        return eventName;
    }

    private EventSendStatus sendStatus;

    public EventSendStatus getSendStatus() {
        return sendStatus;
    }


    public DistributedChannelProvider getChannelProvider() {
        ChannelProvider channelProvider = EventChannelManager.get(eventName).getChannelProvider();
        if (channelProvider instanceof DistributedChannelProvider)
            return (DistributedChannelProvider) channelProvider;
        return null;
    }

    public void commit() {
        this.sendStatus = EventSendStatus.commited;
        EventCommitManager.putEventData(this);
        try {
            getChannelProvider().commitMessage(this.getEventName(), this.getData());
            this.sendStatus = EventSendStatus.success;
        } catch (Exception ex) {
            this.sendStatus = EventSendStatus.fail;
        } finally {
            EventCommitManager.putEventData(this);
        }
    }
}

