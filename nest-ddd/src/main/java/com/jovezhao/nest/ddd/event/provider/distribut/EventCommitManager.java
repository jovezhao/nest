package com.jovezhao.nest.ddd.event.provider.distribut;

import com.jovezhao.nest.cache.CacheContext;

import java.util.ArrayList;
import java.util.List;

/**
 * 事件提交管理器
 * 使用缓存机制存放消息发送状态，对失败的消息进行补偿处理
 * Created by zhaofujun on 2017/6/21.
 */
public class EventCommitManager {
    static CacheContext eventContext;

    static {
        eventContext = CacheContext.getContextByCode("eventContext");
    }

    public static void putEventData(EventData eventData) {
        eventContext.put(eventData.getEventName(), eventData);
    }

    /**
     * 开启补偿器
     */
    public static void startCompensator() {

        // 将发送失败的消息重新使用消息中间件发送
       for(String eventName: eventContext.getKeys()){
           EventData eventData = eventContext.get(EventData.class, eventName);
           eventData.commit();
       }
    }
}
