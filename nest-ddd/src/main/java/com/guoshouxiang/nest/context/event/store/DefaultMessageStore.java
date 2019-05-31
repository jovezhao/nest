package com.guoshouxiang.nest.context.event.store;

import com.guoshouxiang.nest.context.event.message.MessageRecord;

public class DefaultMessageStore implements MessageStore {


    @Override
    public void save(MessageRecord messageRecord) {
    }

    @Override
    public boolean isSucceed(String messageId, String handlerName) {
        return false;
    }
}
