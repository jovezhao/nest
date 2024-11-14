package com.zhaofujun.nest.config;

import java.util.List;

import com.zhaofujun.nest.ddd.event.EventItem;

public class EventConfig {
    private List<EventItem> eventItems;
    private EventHandlerConfig handlerConfig;
    private EventSenderConfig senderConfig;

    public List<EventItem> getEventItems() {
        return eventItems;
    }

    public void setEventItems(List<EventItem> eventItems) {
        this.eventItems = eventItems;
    }

    public EventHandlerConfig getHandlerConfig() {
        return handlerConfig;
    }

    public void setHandlerConfig(EventHandlerConfig handlerConfig) {
        this.handlerConfig = handlerConfig;
    }

    public EventSenderConfig getSenderConfig() {
        return senderConfig;
    }

    public void setSenderConfig(EventSenderConfig senderConfig) {
        this.senderConfig = senderConfig;
    }

}
