package com.jovezhao.nest.activemq;

import com.jovezhao.nest.ddd.event1.IEventHandler;

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
        try {
            this.handler.handle(dataDto.getData());
            return true;
        }catch (Exception ex){
            ex.printStackTrace();

            return false;
        }
    }
}
