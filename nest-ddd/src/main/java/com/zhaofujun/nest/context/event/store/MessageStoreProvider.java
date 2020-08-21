package com.zhaofujun.nest.context.event.store;

import com.zhaofujun.nest.context.event.message.MessageRecord;
import com.zhaofujun.nest.provider.Provider;

public interface MessageStoreProvider extends Provider {

    void save(MessageRecord messageRecord);

    boolean isSucceed(String messageId, String handlerName);
}

