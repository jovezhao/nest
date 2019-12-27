package com.zhaofujun.nest.context.event.message;

import com.zhaofujun.nest.core.BeanFinder;
import com.zhaofujun.nest.json.JsonCreator;
import com.zhaofujun.nest.json.ParameterizedTypeFactory;

import java.lang.reflect.Type;

public class DefaultMessageConverter implements MessageConverter {

    private BeanFinder beanFinder;
    private JsonCreator jsonCreator;

    public DefaultMessageConverter(BeanFinder beanFinder) {
        this.beanFinder = beanFinder;
        jsonCreator = new JsonCreator(beanFinder);
    }


    public String messageToString(MessageInfo messageInfo) {
        return jsonCreator.toJsonString(messageInfo);
    }

    public MessageInfo jsonToMessage(String messageJson, Class eventDataClass) {

        Type type = ParameterizedTypeFactory.make(MessageInfo.class, eventDataClass);
        MessageInfo messageInfo = jsonCreator.toObj(messageJson, type);
        return messageInfo;

    }

}
