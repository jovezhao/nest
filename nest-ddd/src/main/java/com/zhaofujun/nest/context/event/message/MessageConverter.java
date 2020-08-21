package com.zhaofujun.nest.context.event.message;

import com.zhaofujun.nest.provider.Provider;

public interface MessageConverter extends Provider {

    String messageToString(MessageInfo messageInfo);

    MessageInfo jsonToMessage(String messageJson, Class eventDataClass);
}
