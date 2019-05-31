package com.guoshouxiang.nest.context.event.store;

import com.guoshouxiang.nest.context.event.message.MessageRecord;

public interface MessageStore {

    void save(MessageRecord messageRecord);

    boolean isSucceed(String messageId, String handlerName);
}

