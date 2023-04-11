package com.zhaofujun.nest3.application.event;

import com.zhaofujun.nest3.application.NestApplication;
import com.zhaofujun.nest3.application.context.ServiceContext;
import com.zhaofujun.nest3.model.EventData;

public class EventInfo {
    private EventData eventData;
    private long delaySecond;
    private String eventSource;

    public EventData getEventData() {
        return eventData;
    }

    public long getDelaySecond() {
        return delaySecond;
    }

    public String getEventSource() {
        return eventSource;
    }

    public EventInfo(EventData eventData,  String eventSource,long delaySecond) {
        this.eventData = eventData;
        this.delaySecond = delaySecond;
        this.eventSource = eventSource;
    }
    public void attach() {
        ServiceContext serviceContext = NestApplication.currentServiceContext();
        if (serviceContext != null)
            serviceContext.addEvent(this);
    }
}
