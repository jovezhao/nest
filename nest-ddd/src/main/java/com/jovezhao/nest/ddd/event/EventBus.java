package com.jovezhao.nest.ddd.event;

import java.io.Serializable;

/**
 * Created by zhaofujun on 2017/6/21.
 */
public class EventBus {
    /**
     * 发布事件
     *
     * @param eventName
     * @param eventData
     */
    public static void publish(String eventName, Serializable eventData) {

        // 1. 使用事件通道管理器查询通道信息
        EventConfigItem eventConfigItem = EventConfigManager.get(eventName);
        // 2. 使用事件通道发送消息
        eventConfigItem.getChannelProvider().sendMessage(eventName,eventData);
    }

    /*
     * 订阅事件
     *
     * @param eventHandler
     */
    public static void registerHandler(EventHandler eventHandler) {

        //1. 使用事件通道管理器查询通道信息
        EventConfigItem eventConfigItem = EventConfigManager.get(eventHandler.getEventName());
        eventConfigItem.getChannelProvider().subscribe(eventHandler);

    }
}

