package com.zhaofujun.nest.context.event.delay;

import com.zhaofujun.nest.context.event.message.MessageBacklog;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class DelayMessageBacklog implements Delayed {

    private MessageBacklog messageBacklog;
    private LocalDateTime sendTime;


    public MessageBacklog getMessageBacklog() {
        return messageBacklog;
    }


    public LocalDateTime getSendTime() {
        return sendTime;
    }


    public String getMessageId() {
        return messageBacklog.getMessageInfo().getMessageId();
    }

    public DelayMessageBacklog(MessageBacklog messageBacklog, LocalDateTime sendTime) {
        this.messageBacklog = messageBacklog;
        this.sendTime = sendTime;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return sendTime.toInstant(ZoneOffset.of("+8")).toEpochMilli() - System.currentTimeMillis();
    }

    @Override
    public int compareTo(Delayed o) {
        if (this == o)
            return 1;
        if (o == null)
            return -1;
        long diff = this.getDelay(TimeUnit.NANOSECONDS) - o.getDelay(TimeUnit.NANOSECONDS);
        return diff < 0 ? -1 : (diff == 0 ? 0 : 1);

    }
}
