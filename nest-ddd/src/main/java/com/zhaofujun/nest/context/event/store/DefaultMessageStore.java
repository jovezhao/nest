package com.zhaofujun.nest.context.event.store;

import com.zhaofujun.nest.context.event.message.MessageRecord;

public class DefaultMessageStore implements MessageStore {


    @Override
    public void save(MessageRecord messageRecord) {
    }

    @Override
    public boolean isSucceed(String messageId, String handlerName) {
        return false;
    }
}
