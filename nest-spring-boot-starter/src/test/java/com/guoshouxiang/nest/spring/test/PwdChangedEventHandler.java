package com.guoshouxiang.nest.spring.test;

import com.guoshouxiang.nest.context.event.EventArgs;
import com.guoshouxiang.nest.context.event.EventHandler;
import com.guoshouxiang.nest.spring.test.models.PasswordChangedEventData;

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
        System.out.println(eventData.toString());
    }
}

