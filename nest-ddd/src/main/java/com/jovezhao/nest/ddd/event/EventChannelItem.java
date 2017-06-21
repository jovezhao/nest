package com.jovezhao.nest.ddd.event;

public class EventChannelItem{
    private String eventName;
    private ChannelProvider channelProvider;


    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public ChannelProvider getChannelProvider() {
        return channelProvider;
    }

    public void setChannelProvider(ChannelProvider channelProvider) {
        this.channelProvider = channelProvider;
    }
}
