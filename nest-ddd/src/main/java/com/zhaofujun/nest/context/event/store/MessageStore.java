package com.zhaofujun.nest.context.event.store;

import com.zhaofujun.nest.context.event.message.MessageRecord;

public interface MessageStore {

    void save(MessageRecord messageRecord);

    boolean isSucceed(String messageId, String handlerName);
}

