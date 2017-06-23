package com.jovezhao.nest.ddd.event.provider.distribut;

import com.jovezhao.nest.ddd.event.EventHandler;
import com.jovezhao.nest.utils.JsonUtils;

/**
 * 幂等处理事件消息
 * Created by zhaofujun on 2017/6/22.
 */
public class EventDataProcessor {
    private EventData eventData;
    private EventHandler eventHandler;

    public EventDataProcessor(EventHandler eventHandler) {

        this.eventHandler = eventHandler;
    }

    public void setEventData(String eventData) {
        this.eventData = JsonUtils.toObj(eventData,EventData.class);
    }

    public void process() throws Exception {

        if (EventRecordManager.isProcess(eventData.getDataId())) {
            EventRecordManager.putEventData(eventData);
            return;
        }


        //从消息日志中查看是否有处理过本消息，如果已经处理则直接跳出
        Object data = JsonUtils.toObj(eventData.getData(), eventHandler.getTClass());
        eventHandler.handle(data);
        EventRecordManager.putEventData(eventData);
    }


}
