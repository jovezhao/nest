package com.zhaofujun.nest.context.event.message;

public interface MessageConverter {

    String messageToString(MessageInfo messageInfo);

    MessageInfo jsonToMessage(String messageJson, Class eventDataClass);
}
