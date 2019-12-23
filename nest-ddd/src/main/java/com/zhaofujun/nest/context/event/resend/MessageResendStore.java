package com.zhaofujun.nest.context.event.resend;

import com.zhaofujun.nest.context.event.message.MessageBacklog;
import com.zhaofujun.nest.context.event.store.MessageStore;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;

public interface  MessageResendStore{
    void add(MessageBacklog messageBacklog);

}
