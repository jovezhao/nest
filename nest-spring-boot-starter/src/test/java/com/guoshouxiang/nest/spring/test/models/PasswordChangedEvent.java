package com.guoshouxiang.nest.spring.test.models;

import com.guoshouxiang.nest.context.event.BaseEvent;

public class PasswordChangedEvent extends BaseEvent<PasswordChangedEventData> {


    public PasswordChangedEvent(PasswordChangedEventData eventObject, String source) {
        super(eventObject, source);
    }

    @Override
    public String getEventCode() {
        return "PASSWORD_CHANGED";
    }
}
