package com.zhaofujun.nest3.application.event;

import java.io.Serializable;
import java.time.LocalDateTime;

public class MessageInfo implements Serializable {
    private String messageId;
    private String eventSource;
    private LocalDateTime sendTime;
    private String messageChannelProviderCode;
    private String eventCode;
    private String eventData;
    private String eventDataType;
    private String extendInfo;

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getEventSource() {
        return eventSource;
    }

    public void setEventSource(String eventSource) {
        this.eventSource = eventSource;
    }

    public LocalDateTime getSendTime() {
        return sendTime;
    }

    public void setSendTime(LocalDateTime sendTime) {
        this.sendTime = sendTime;
    }

    public String getMessageChannelProviderCode() {
        return messageChannelProviderCode;
    }

    public void setMessageChannelProviderCode(String messageChannelProviderCode) {
        this.messageChannelProviderCode = messageChannelProviderCode;
    }

    public String getEventCode() {
        return eventCode;
    }

    public void setEventCode(String eventCode) {
        this.eventCode = eventCode;
    }

    public String getEventData() {
        return eventData;
    }

    public void setEventData(String eventData) {
        this.eventData = eventData;
    }

    public String getExtendInfo() {
        return extendInfo;
    }

    public void setExtendInfo(String extendInfo) {
        this.extendInfo = extendInfo;
    }

    public String getEventDataType() {
        return eventDataType;
    }

    public void setEventDataType(String eventDataType) {
        this.eventDataType = eventDataType;
    }
}
