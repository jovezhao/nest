package com.zhaofujun.nest.context.event.message;

import java.io.Serializable;

public class MessageBacklog implements Serializable {
    private String eventCode;
    private MessageInfo messageInfo;

    public MessageBacklog(String eventCode, MessageInfo messageInfo) {
        this.eventCode = eventCode;
        this.messageInfo = messageInfo;
    }

    public String getEventCode() {
        return eventCode;
    }


    public MessageInfo getMessageInfo() {
        return messageInfo;
    }

}