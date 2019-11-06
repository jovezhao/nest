package com.zhaofujun.nest.spring.test;

import com.zhaofujun.nest.context.event.EventArgs;
import com.zhaofujun.nest.context.event.EventHandler;
import com.zhaofujun.nest.spring.test.models.PasswordChangedEventData;

public class PwdChangedEventHandler implements EventHandler<PasswordChangedEventData> {
    public static final String EVENT_CODE = "PASSWORD_CHANGED";

    @Override
    public String getEventCode() {
        return EVENT_CODE;
    }

    @Override
    public Class<PasswordChangedEventData> getEventDataClass() {
        return PasswordChangedEventData.class;
    }

    @Override
    public void handle(PasswordChangedEventData eventData, EventArgs eventArgs) {
        System.out.println("接收事件"+eventData.toString());
    }
}

