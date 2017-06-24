package com.jovezhao.nest.ddd.event.provider.distribut;

import com.jovezhao.nest.cache.CacheContext;

/**
 * 事件记录管理器
 * Created by zhaofujun on 2017/6/22.
 */
public class EventRecordManager {
    static CacheContext eventContext;

    static {
        eventContext = CacheContext.getContextByCode("eventRecord");
    }

    public static void putEventData(MessageData messageData) {
        eventContext.put(messageData.getMessageId(), messageData);
    }

    public static boolean isProcess(String dataId) {
        return eventContext.containsKey(dataId);
    }
}
