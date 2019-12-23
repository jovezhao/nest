package com.zhaofujun.nest.context.event.message;

import com.google.gson.JsonObject;
import com.zhaofujun.nest.core.BeanFinder;
import com.zhaofujun.nest.core.EventData;
import com.zhaofujun.nest.json.JsonCreator;

import java.text.SimpleDateFormat;
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
        JsonObject jsonObject = jsonCreator.toObj(messageJson, JsonObject.class);
        MessageInfo messageInfo = new MessageInfo();
        messageInfo.setEventSource(jsonObject.get("eventSource").getAsString());
        messageInfo.setMessageId(jsonObject.get("messageId").getAsString());
        try {
            messageInfo.setSendTime(new SimpleDateFormat().parse(jsonObject.get("sendTime").getAsString()));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        String eventDataJson = jsonObject.get("data").getAsString();
        Object o = jsonCreator.toObj(eventDataJson, eventDataClass);
        messageInfo.setData((EventData) o);
        return messageInfo;
    }

    public <T extends EventData> T toEventData(MessageInfo messageInfo, Class<T> tClass) {
//        String message = messageInfo.getData();
//        return jsonCreator.toObj(message, tClass);
        return (T) messageInfo.getData();
    }
}
