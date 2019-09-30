package com.zhaofujun.nest.spring.test.models;

import com.zhaofujun.nest.context.event.BaseEvent;

public class PasswordChangedEvent extends BaseEvent<PasswordChangedEventData> {


    public PasswordChangedEvent(PasswordChangedEventData eventObject, String source) {
        super(eventObject, source);
    }

    @Override
    public String getEventCode() {
        return "PASSWORD_CHANGED";
    }
}
