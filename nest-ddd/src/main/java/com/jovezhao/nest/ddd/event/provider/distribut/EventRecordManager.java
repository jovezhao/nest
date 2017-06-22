package com.jovezhao.nest.ddd.event.provider.distribut;

import com.jovezhao.nest.cache.CacheContext;

/**
 * Created by zhaofujun on 2017/6/22.
 */
public class EventRecordManager {
    static CacheContext eventContext;

    static {
        eventContext = CacheContext.getContextByCode("eventRecord");
    }

    public static void putEventData(EventData eventData) {
        eventContext.put(eventData.getDataId().toValue(), eventData);
    }

    public static boolean isProcess(String dataId) {
        return eventContext.containsKey(dataId);
    }
}
