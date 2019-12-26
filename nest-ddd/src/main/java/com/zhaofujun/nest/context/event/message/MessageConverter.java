package com.zhaofujun.nest.context.event.message;

import com.zhaofujun.nest.core.BeanFinder;
import com.zhaofujun.nest.core.EventData;
import com.zhaofujun.nest.json.JsonCreator;
import com.zhaofujun.nest.json.ParameterizedTypeFactory;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.UUID;

public class MessageConverter {

    private BeanFinder beanFinder;
    private JsonCreator jsonCreator;

    public MessageConverter(BeanFinder beanFinder) {
        this.beanFinder = beanFinder;
        jsonCreator = new JsonCreator(beanFinder);
    }

    public MessageInfo fromEvent(EventData eventData) {
        MessageInfo messageInfo = new MessageInfo();
        messageInfo.setMessageId(UUID.randomUUID().toString());
        messageInfo.setData(eventData);
        messageInfo.setEventSource("?");
        messageInfo.setSendTime(new Date());

        return messageInfo;
    }

    public MessageInfo fromString(String messageJson, Class eventDataClass) {

        Type type = ParameterizedTypeFactory.make(MessageInfo.class, eventDataClass);
        MessageInfo messageInfo = jsonCreator.toObj(messageJson, type);
        return messageInfo;

    }

    public <T extends EventData> T toEventData(MessageInfo messageInfo, Class<T> tClass) {
        return (T) messageInfo.getData();
    }
}
