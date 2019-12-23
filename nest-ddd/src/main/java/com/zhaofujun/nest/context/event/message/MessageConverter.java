package com.zhaofujun.nest.context.event.message;

import com.zhaofujun.nest.core.BeanFinder;
import com.zhaofujun.nest.core.EventData;
import com.zhaofujun.nest.json.JsonCreator;

import java.util.Date;
import java.util.UUID;

public class MessageConverter {

    private BeanFinder beanFinder;
    private JsonCreator jsonCreator;

    public MessageConverter(BeanFinder beanFinder) {
        this.beanFinder = beanFinder;
        jsonCreator=new JsonCreator(beanFinder);
    }

    public  MessageInfo fromEvent(EventData eventData) {
        MessageInfo messageInfo = new MessageInfo();
        messageInfo.setMessageId(UUID.randomUUID().toString());
        messageInfo.setData(eventData);
        messageInfo.setEventSource("?");
        messageInfo.setSendTime(new Date());

        return messageInfo;
    }
    public  <T extends EventData> T toEventData(MessageInfo messageInfo, Class<T> tClass) {
//        String message = messageInfo.getData();
//        return jsonCreator.toObj(message, tClass);
        return (T)messageInfo.getData();
    }
}
