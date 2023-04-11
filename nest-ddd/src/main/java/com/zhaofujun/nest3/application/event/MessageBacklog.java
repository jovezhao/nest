package com.zhaofujun.nest3.application.event;


import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class MessageBacklog implements Delayed {


    private String eventCode;
    private String messageInfoString;
    private String eventDataType;
    private String messageId;
    private String batchNo;
    private String messageChannelProviderCode;

    private LocalDateTime sendTime;


    public MessageBacklog(String eventCode, String messageInfoString, String eventDataType, String messageId, LocalDateTime sendTime) {
        this.eventCode = eventCode;
        this.messageInfoString = messageInfoString;
        this.eventDataType = eventDataType;
        this.messageId = messageId;
        this.sendTime = sendTime;
    }

    public LocalDateTime getSendTime() {
        return sendTime;
    }


//    public String getMessageId() {
//        return messageBacklog.getMessageInfo().getMessageId();
//    }



    @Override
    public long getDelay(TimeUnit unit) {
        return sendTime.toInstant(ZoneOffset.of("+8")).toEpochMilli() - System.currentTimeMillis();
    }

    public String getEventCode() {
        return eventCode;
    }

    public String getMessageInfoString() {
        return messageInfoString;
    }

    public String getEventDataType() {
        return eventDataType;
    }

    public String getMessageId() {
        return messageId;
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
