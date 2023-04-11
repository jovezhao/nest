package com.zhaofujun.nest3.application.context;

import com.zhaofujun.nest3.application.event.MessageInfo;

public interface MessageDispatcher {
    void receive(MessageInfo messageInfo);

    void commit();

    void runRetryWork();
}
