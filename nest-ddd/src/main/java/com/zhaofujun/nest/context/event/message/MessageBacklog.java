package com.zhaofujun.nest.context.event.message;

import java.io.Serializable;
import java.lang.reflect.Type;

public class MessageBacklog implements Serializable {
    private String eventCode;
    private String messageInfoString;
    private String eventDataType;

    public MessageBacklog(String eventCode, String messageInfoString, String eventDataType) {
        this.eventCode = eventCode;
        this.messageInfoString = messageInfoString;
        this.eventDataType = eventDataType;
    }

    public String getEventCode() {
        return eventCode;
    }

    public String getMessageInfoString() {
        return messageInfoString;
    }

    public String getEventDataType() {
        return eventDataType;
    }
}