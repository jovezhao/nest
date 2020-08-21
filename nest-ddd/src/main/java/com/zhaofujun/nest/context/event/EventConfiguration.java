package com.zhaofujun.nest.context.event;

import com.zhaofujun.nest.configuration.ConfigurationItem;

public class EventConfiguration extends ConfigurationItem {

    private String messageChannelCode;

    public String getMessageChannelCode() {
        return messageChannelCode;
    }

    public void setMessageChannelCode(String messageChannelCode) {
        this.messageChannelCode = messageChannelCode;
    }

}

