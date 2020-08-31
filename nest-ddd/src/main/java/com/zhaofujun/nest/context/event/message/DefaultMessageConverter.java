package com.zhaofujun.nest.context.event.message;

import com.zhaofujun.nest.json.JsonCreator;
import com.zhaofujun.nest.json.ParameterizedTypeFactory;

import java.lang.reflect.Type;

public class DefaultMessageConverter implements MessageConverter {

    public static final String CODE = "DefaultMessageConverter";

    private JsonCreator jsonCreator =  JsonCreator.getInstance();



    public String messageToString(MessageInfo messageInfo) {
        return jsonCreator.toJsonString(messageInfo);
    }

    public MessageInfo jsonToMessage(String messageJson, Class eventDataClass) {

        Type type = ParameterizedTypeFactory.make(MessageInfo.class, eventDataClass);
        MessageInfo messageInfo = jsonCreator.toObj(messageJson, type);
        return messageInfo;

    }

    @Override
    public String getCode() {
        return CODE;
    }
}
