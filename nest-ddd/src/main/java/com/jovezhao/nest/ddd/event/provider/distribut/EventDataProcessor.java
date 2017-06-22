package com.jovezhao.nest.ddd.event.provider.distribut;

import com.jovezhao.nest.ddd.event.EventHandler;
import com.jovezhao.nest.ddd.event.provider.distribut.DistributedEventConsumer;
import com.jovezhao.nest.ddd.event.provider.distribut.EventData;

/**
 * 幂等处理事件消息
 * Created by zhaofujun on 2017/6/22.
 */
public class EventDataProcessor {
    private EventData eventData;
    private EventHandler eventHandler;

    public EventDataProcessor(EventData eventData, EventHandler eventHandler) {
        this.eventData = eventData;
        this.eventHandler = eventHandler;
    }

    public void process() throws Exception{
        if (EventRecordManager.isProcess(eventData.getDataId()))
            return;
        //从消息日志中查看是否有处理过本消息，如果已经处理则直接跳出
        eventHandler.handle(eventData.getData());
        EventRecordManager.putEventData(eventData);
    }


}
