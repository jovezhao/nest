package com.guoshouxiang.nest.context.event.message;

import com.guoshouxiang.nest.context.event.BaseEvent;
import com.guoshouxiang.nest.context.event.EventData;
import com.guoshouxiang.nest.utils.JsonUtils;

import java.util.Date;
import java.util.UUID;

public class MessageConverter {
    public static MessageInfo fromEvent(BaseEvent baseEvent) {
        MessageInfo messageInfo = new MessageInfo();
        messageInfo.setMessageId(UUID.randomUUID().toString());
        messageInfo.setData(JsonUtils.toJsonString(baseEvent.getEventObject()).getBytes());
        messageInfo.setEventSource(baseEvent.getSource());
        messageInfo.setSendTime(new Date());

        return messageInfo;
    }

    public static MessageInfo fromEvent(EventData eventData) {
        MessageInfo messageInfo = new MessageInfo();
        messageInfo.setMessageId(UUID.randomUUID().toString());
        messageInfo.setData(JsonUtils.toJsonString(eventData).getBytes());
        messageInfo.setEventSource("?");
        messageInfo.setSendTime(new Date());

        return messageInfo;
    }
    public static <T extends EventData> T toEventData(MessageInfo messageInfo, Class<T> tClass) {
        String message = new String(messageInfo.getData());
        return JsonUtils.toObj(message, tClass);
    }
}
