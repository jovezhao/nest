package com.ywkj.nest.kafka;

import com.ywkj.nest.ddd.IEventHandler;

class EventWork {
    String eventName;
    IEventHandler handler;

    public EventWork(String eventName, IEventHandler handler) {
        this.eventName = eventName;
        this.handler = handler;
    }

    public String getEventName() {
        return eventName;
    }

    public String getHandlerName() {
        return this.handler.getClass().getName().replace(".", "").replace("@", "").replace("$","");
    }

    public boolean doWork(Object object) {
        EventDataDto dataDto = (EventDataDto) object;
        this.handler.handle(dataDto.getData());
        return true;
    }
}
