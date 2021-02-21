package com.zhaofujun.nest.context.event.delay;


import com.zhaofujun.nest.context.event.message.MessageBacklog;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.DelayQueue;

public class DefaultDelayMessageStore implements DelayMessageStore {

    public static final String CODE = "DefaultDelayMessageStore";

    DelayQueue<DelayMessageBacklog> queue = new DelayQueue<DelayMessageBacklog>();

    @Override
    public String getCode() {
        return CODE;
    }

    @Override
    public void add(DelayMessageBacklog delayMessageBacklog) {
        queue.add(delayMessageBacklog);
    }

    @Override
    public List<MessageBacklog> getAndLock() {
        List<MessageBacklog> messageBacklogs = new ArrayList<>();

        while (true) {
            DelayMessageBacklog backlog = queue.poll();
            if (backlog == null) break;
            messageBacklogs.add(backlog.getMessageBacklog());
        }
        return messageBacklogs;
    }

    @Override
    public void clear(String messageId) {

    }
}
