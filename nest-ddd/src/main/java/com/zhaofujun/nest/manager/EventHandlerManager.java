package com.zhaofujun.nest.manager;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.zhaofujun.nest.ddd.ConsumeMode;
import com.zhaofujun.nest.ddd.EventHandler;

/*
 * 管理所有事件处理器
 */
public class EventHandlerManager {
    @SuppressWarnings("rawtypes")
    private static Map<String, Set<EventHandler>> handlerMap = new HashMap<>();

    @SuppressWarnings("rawtypes")
    public static void addEventHandler(EventHandler... eventHandlers) {
        addEventHandler(Arrays.asList(eventHandlers));
    }

    @SuppressWarnings("rawtypes")
    public static void addEventHandler(Collection<EventHandler> eventHandlers) {
        eventHandlers.stream()
                .collect(Collectors.groupingBy(p -> p.getEventName()))
                .forEach((eventName, handlers) -> {

                    // 将没有配置通道的事件放入默认通道
                    EventManager.checkEventName(eventName);
                    
                    var handlerSet = handlerMap.get(eventName);
                    if (handlerSet == null) {
                        handlerSet = new HashSet<>();
                        handlerMap.put(eventName, handlerSet);
                    }
                    handlerSet.addAll(handlers);
                });
    }

    @SuppressWarnings("rawtypes")
    public static Map<ConsumeMode, List<EventHandler>> getEventHandlers(String eventName) {
        Set<EventHandler> handlerSet = handlerMap.get(eventName);
        if (handlerSet == null)
            handlerSet = new HashSet<>();
        return handlerSet.stream().collect(Collectors.groupingBy(p -> p.getConsumeMode()));
    }
}
