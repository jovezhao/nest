package com.zhaofujun.nest.ddd.event;

import java.util.Collection;
import com.zhaofujun.nest.ddd.EventHandler;

/**
 * 通道消费者接口，负责注册事件处理器和设置事件应用服务。
 */
public interface ChannelConsumer {

    /**
     * 注册事件处理器。
     *
     * @param eventName     事件名称
     * @param eventHandlers 事件处理器集合
     */
    void register(String eventName, Collection<EventHandler> eventHandlers);

    /**
     * 设置事件应用服务。
     *
     * @param appService 事件应用服务
     */
    void setEventAppService(EventAppService appService);
}
