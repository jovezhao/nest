package com.jovezhao.nest.ddd.event.provider.dft;

import java.io.Serializable;

/**
 * Created by zhaofujun on 2017/6/22.
 */
public class DefaultEventData {
    private Serializable eventData;
    private String eventName;

    public Serializable getEventData() {
        return eventData;
    }


    public String getEventName() {
        return eventName;
    }

    public DefaultEventData(Serializable eventData, String eventName) {
        this.eventData = eventData;
        this.eventName = eventName;
    }
}
