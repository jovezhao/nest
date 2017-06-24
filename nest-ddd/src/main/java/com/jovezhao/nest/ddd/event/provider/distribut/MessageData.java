package com.jovezhao.nest.ddd.event.provider.distribut;

import java.io.Serializable;

/**
 * 分布式消息
 * Created by zhaofujun on 2017/6/21.
 */
public class MessageData implements Serializable {
    private String message;
    private String messageId;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

}

