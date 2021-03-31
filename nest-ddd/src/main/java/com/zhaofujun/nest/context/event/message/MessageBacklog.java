package com.zhaofujun.nest.context.event.message;

import java.io.Serializable;
import java.lang.reflect.Type;

public class MessageBacklog implements Serializable {
    private String eventCode;
    private String messageInfoString;
    private String eventDataType;
    private String messageId;

    public MessageBacklog(String eventCode, String messageInfoString, String eventDataType, String messageId) {
        this.eventCode = eventCode;
        this.messageInfoString = messageInfoString;
        this.eventDataType = eventDataType;
        this.messageId = messageId;
    }

    public String getEventCode() {
        return eventCode;
    }

    public String getMessageInfoString() {
        return messageInfoString;
    }

    public String getMessageId() {
        return messageId;
    }

    public String getEventDataType() {
        return eventDataType;
    }
}