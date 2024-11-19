package com.zhaofujun.nest.ddd.event;

import com.zhaofujun.nest.ddd.Identifier;

public class ProcessIdentifier extends Identifier {

    private String messageId;
    private String handlerId;

    public ProcessIdentifier(String messageId, String handlerId) {
        this.messageId = messageId;
        this.handlerId = handlerId;
    }

    public String getMessageId() {
        return messageId;
    }

    public String getHandlerId() {
        return handlerId;
    }

    @Override
    public String toValue() {
        return messageId + "-" + handlerId;
    }

}