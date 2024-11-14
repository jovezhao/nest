package com.zhaofujun.nest.manager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.zhaofujun.nest.NestConst;
import com.zhaofujun.nest.ddd.event.EventItem;

public class EventManager {
    private static List<EventItem> eventList = new ArrayList<>();

    public static void addEventItem(EventItem... eventItems) {
        addEventItem(Arrays.asList(eventItems));
    }

    public static void checkEventName(String eventName) {
        // 检查事件，如果没有配置通道的话，自动配置到默认通道
        if (eventList.stream().filter(p -> p.getEventName().equals(eventName)).count() == 0) {
            var eventItem = new EventItem();
            eventItem.setChannelCode(NestConst.defaultChannel);
            eventItem.setEventName(eventName);
            eventList.add(eventItem);
        }   
    }

    public static void addEventItem(Collection<EventItem> eventItems) {
        eventList.addAll(eventItems);
    }

    public static List<String> getEventNames(String channelCode) {
        return eventList.stream()
                .filter(p -> p.getChannelCode().equals(channelCode))
                .map(p -> p.getEventName())
                .collect(Collectors.toList());
    }

    public static String getChannelCode(String eventName) {
        return eventList.stream()
                .filter(p -> p.getEventName().equals(eventName))
                .map(p -> p.getChannelCode())
                .findFirst()
                .orElse(NestConst.defaultChannel);
    }
}