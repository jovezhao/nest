package com.zhaofujun.nest.context.event.resend;

import com.zhaofujun.nest.context.event.message.MessageBacklog;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;

public class DefaultMessageResendStore implements MessageResendStore {

    public static final String CODE = "DefaultMessageResendStore";
    public Queue<MessageBacklog> messageBacklogs = new LinkedBlockingDeque<>();

    public void add(MessageBacklog messageBacklog) {
        messageBacklogs.add(messageBacklog);
    }

    public void Resend() {
        MessageBacklog messageBacklog = messageBacklogs.poll();
        //todo resend
    }

    @Override
    public String getCode() {
        return CODE;
    }
}
