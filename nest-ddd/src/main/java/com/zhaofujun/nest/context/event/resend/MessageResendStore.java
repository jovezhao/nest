package com.zhaofujun.nest.context.event.resend;

import com.zhaofujun.nest.context.event.message.MessageBacklog;
import com.zhaofujun.nest.provider.Provider;


public interface  MessageResendStore extends Provider {

    void add(MessageBacklog messageBacklog);

    MessageBacklog pollOne();
}
