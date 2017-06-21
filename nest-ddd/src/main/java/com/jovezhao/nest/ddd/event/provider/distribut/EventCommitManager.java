package com.jovezhao.nest.ddd.event.provider.distribut;

import com.jovezhao.nest.cache.CacheContext;

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

    public static void putEventData(DistributedEventInfo distributedEventInfo) {
        eventContext.put(distributedEventInfo.getEventName(), distributedEventInfo);
    }

    /**
     * 执行补偿，该方法最好通过外部服务自动定时执行
     */
    public static void compensate() {

        // 将发送失败的消息重新使用消息中间件发送
        for (String eventName : eventContext.getKeys()) {
            DistributedEventInfo distributedEventInfo = eventContext.get(DistributedEventInfo.class, eventName);
            if (distributedEventInfo.getSendStatus() == EventSendStatus.fail)
                distributedEventInfo.commit();
            if (distributedEventInfo.getSendStatus() == EventSendStatus.success)
                eventContext.remove(eventName);

            // todo  可以引入监控机制，当多次提交事件都失败时发起警告
        }
    }
}
