package com.jovezhao.nest.ddd.event;

/**
 * 事件配置项
 * 用于定义事件与通道之间的关系，一个事件只能使用一个通道，
 */
public class EventConfigItem {
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
