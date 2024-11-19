package com.zhaofujun.nest.utils;

import java.time.LocalDateTime;

import com.zhaofujun.nest.ddd.LongIdentifier;
import com.zhaofujun.nest.ddd.event.EventMessageModel;

public class EventUtil {
    public static void publish(String eventName, Object eventData, long delaySecond) {
        long idLong = SnowFlakeUtil.nextLong();
        LocalDateTime sendDateTime = LocalDateTime.now().plusSeconds(delaySecond);
        new EventMessageModel<>(new LongIdentifier(idLong), eventName, eventData, sendDateTime);
    }

    public static void publish(String eventName, Object eventData) {
        publish(eventName, eventData, 0);
    }

}
