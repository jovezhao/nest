package com.jovezhao.nest.ddd.event.provider.distribut;

import com.jovezhao.nest.ddd.event.ChannelProvider;
import com.jovezhao.nest.ddd.event.EventChannelManager;
import com.jovezhao.nest.ddd.identifier.IdGenerator;

import java.io.Serializable;

/**
 * 服务事件，用于创建一个事件
 * Created by Jove on 2016-03-21.
 */
public class DistributedEventInfo {

    public EventData getEventData() {
        return eventData;
    }

    public static DistributedEventInfo createEvent(String eventName, Serializable data) {
        DistributedEventInfo eventInfo = new DistributedEventInfo();
        eventInfo.eventData = new EventData();
        eventInfo.eventData.setData(data);
        eventInfo.eventData.setDataId(IdGenerator.getInstance().generate(EventData.class));
        eventInfo.eventName = eventName;
        eventInfo.sendStatus = EventSendStatus.wait;

        return eventInfo;
    }

    private DistributedEventInfo() {
    }

    private EventData eventData;
    private String eventName;
    private EventSendStatus sendStatus;


    public String getEventName() {
        return eventName;
    }


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
            getChannelProvider().commitMessage(this.getEventName(), this.getEventData());
            this.sendStatus = EventSendStatus.success;
        } catch (Exception ex) {
            this.sendStatus = EventSendStatus.fail;
        } finally {
            EventCommitManager.putEventData(this);
        }
    }
}

