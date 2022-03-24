package com.zhaofujun.nest.context.event;

import com.zhaofujun.nest.context.event.message.MessageInfo;
import com.zhaofujun.nest.standard.AppService;
import com.zhaofujun.nest.standard.EventBus;
import com.zhaofujun.nest.standard.EventData;

@AppService
public class ResendAppService {
//    public void send(EventData eventData) {
//        EventBus eventBus = new DefaultEventBus();
//        eventBus.publish(eventData);
//    }

    public void send(MessageInfo messageInfo) {
        new DefaultEventBus().publish(messageInfo);
    }
}
