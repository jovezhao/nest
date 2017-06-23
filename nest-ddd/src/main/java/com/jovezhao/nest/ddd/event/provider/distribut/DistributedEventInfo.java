package com.jovezhao.nest.ddd.event.provider.distribut;

import com.jovezhao.nest.ddd.event.ChannelProvider;
import com.jovezhao.nest.ddd.event.EventChannelManager;
import com.jovezhao.nest.ddd.identifier.IdGenerator;
import com.jovezhao.nest.utils.JsonUtils;

import java.io.Serializable;

/**
 * 分布式事件信息
 * Created by Jove on 2016-03-21.
 */
public class DistributedEventInfo implements Serializable {

    public EventData getEventData() {
        return eventData;
    }

    /**
     * 用于创建一个事件
     *
     * @param eventName
     * @param data
     * @return
     */
    public static DistributedEventInfo createEventInfo(String eventName, Serializable data) {
        DistributedEventInfo eventInfo = new DistributedEventInfo();
        eventInfo.eventData = new EventData();
        eventInfo.eventData.setData(JsonUtils.toJsonString(data));
        eventInfo.eventData.setDataId(IdGenerator.getInstance().generate(EventData.class).toValue());
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


    /**
     * 通过事件通道管理器查找当前事件使用的分布式通道
     * 能进入到这里的事件一定都是分布式通道
     *
     * @return
     */
    public DistributedEventProducer getEventProducer() {
        ChannelProvider channelProvider = EventChannelManager.get(eventName).getChannelProvider();
        return (DistributedEventProducer) channelProvider.getEventProducer();
    }

    public void commit() {
        this.sendStatus = EventSendStatus.commited;
        EventCommitManager.putEventData(this);
        try {
            getEventProducer().commitMessage(this.getEventName(), this.getEventData());
            this.sendStatus = EventSendStatus.success;
        } catch (Exception ex) {
            this.sendStatus = EventSendStatus.fail;
        } finally {
            EventCommitManager.putEventData(this);
        }
    }
}

