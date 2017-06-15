package com.jovezhao.nest.rabbitmq;

import com.jovezhao.nest.ddd.event.IEventHandler;

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
    public  String getHandlerName(){
        return  this.handler.getClass().getName();
    }

    public boolean doWork(Object object) {
        EventDataDto dataDto = (EventDataDto) object;
        this.handler.handle(dataDto.getData());
        return true;
    }
}
