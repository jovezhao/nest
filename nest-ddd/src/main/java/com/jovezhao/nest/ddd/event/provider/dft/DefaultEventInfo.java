package com.jovezhao.nest.ddd.event.provider.dft;

import java.io.Serializable;

/**
 * 默认事件信息
 * Created by zhaofujun on 2017/6/22.
 */
public class DefaultEventInfo {
    private Serializable eventData;
    private String eventName;

    public Serializable getEventData() {
        return eventData;
    }


    public String getEventName() {
        return eventName;
    }

    public DefaultEventInfo(Serializable eventData, String eventName) {
        this.eventData = eventData;
        this.eventName = eventName;
    }
}
