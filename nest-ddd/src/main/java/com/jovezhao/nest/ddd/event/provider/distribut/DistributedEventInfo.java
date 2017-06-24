package com.jovezhao.nest.ddd.event.provider.distribut;

import com.jovezhao.nest.ddd.event.ChannelProvider;
import com.jovezhao.nest.ddd.event.EventConfigManager;
import com.jovezhao.nest.ddd.identifier.IdGenerator;
import com.jovezhao.nest.exception.SystemException;
import com.jovezhao.nest.log.Log;
import com.jovezhao.nest.log.LogAdapter;
import com.jovezhao.nest.utils.JsonUtils;


import java.io.Serializable;

/**
 * 分布式事件信息
 * Created by Jove on 2016-03-21.
 */
public class DistributedEventInfo implements Serializable {

    protected Log log=new LogAdapter(this.getClass());

    public MessageData getMessageData() {
        return messageData;
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
        eventInfo.messageData = new MessageData();
        eventInfo.messageData.setMessage(JsonUtils.toJsonString(data));
        eventInfo.messageData.setMessageId(IdGenerator.getInstance().generate(MessageData.class).toValue());
        eventInfo.eventName = eventName;
        eventInfo.sendStatus = EventSendStatus.wait;

        return eventInfo;
    }

    private DistributedEventInfo() {
    }

    private MessageData messageData;
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
        ChannelProvider channelProvider = EventConfigManager.get(eventName).getChannelProvider();
        return (DistributedEventProducer) channelProvider.getEventProducer();
    }

    public void commit() {
        this.sendStatus = EventSendStatus.commited;
        EventCommitManager.putEventData(this);
        try {
            getEventProducer().commitMessage(this.getEventName(), this.getMessageData());
            this.sendStatus = EventSendStatus.success;
        } catch (SystemException ex) {
            this.sendStatus = EventSendStatus.fail;
            log.warn(ex);
        } finally {
            EventCommitManager.putEventData(this);
        }
    }
}

