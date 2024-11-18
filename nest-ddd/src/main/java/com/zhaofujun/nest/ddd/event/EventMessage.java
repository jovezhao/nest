package com.zhaofujun.nest.ddd.event;

import java.time.LocalDateTime;

import com.zhaofujun.nest.ddd.AggregateRoot;
import com.zhaofujun.nest.ddd.LongIdentifier;

/**
 * 事件消息，用于事件信息的存储，这是发送到消息仓储中的实体
 */
public class EventMessage<T> extends AggregateRoot<LongIdentifier> {
    private T eventData;
    private String eventName;
    private LocalDateTime sendTime;
    private EventState eventState;
    private int sendTimes;
    private LocalDateTime realTime;

    public EventMessage(LongIdentifier id, String eventName, T eventData, LocalDateTime sendTime) {
        super(id);
        this.eventData = eventData;
        this.eventName = eventName;
        this.sendTime = sendTime;
        eventState = EventState.preSend;
    }

    public T getEventData() {
        return eventData;
    }

    public String getEventName() {
        return eventName;
    }

    public LocalDateTime getSendTime() {
        return sendTime;
    }

    public EventState getEventState() {
        return eventState;
    }

    public int getSendTimes() {
        return sendTimes;
    }

    public LocalDateTime getRealTime() {
        return realTime;
    }

    public void publish() {
        this.realTime = LocalDateTime.now();
        this.eventState=EventState.sent;
    }

    public void readyPublish() {
        this.eventState = EventState.sending;
        this.sendTimes++;
    }

    public void fail() {
        this.eventState = EventState.sendFail;
    }
}