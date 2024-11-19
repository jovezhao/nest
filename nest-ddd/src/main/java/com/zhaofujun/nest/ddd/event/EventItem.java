package com.zhaofujun.nest.ddd.event;

/**
 * 事件项，定义一个事件将要使用什么通道传输
 */
public class EventItem {
    /**
     * 事件名称。
     */
    private String eventName;

    /**
     * 通道代码。
     */
    private String channelCode;

    /**
     * 获取事件名称。
     *
     * @return 事件名称
     */
    public String getEventName() {
        return eventName;
    }

    /**
     * 设置事件名称。
     *
     * @param eventName 事件名称
     */
    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    /**
     * 获取通道代码。
     *
     * @return 通道代码
     */
    public String getChannelCode() {
        return channelCode;
    }

    /**
     * 设置通道代码。
     *
     * @param channelCode 通道代码
     */
    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

}
