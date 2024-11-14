package com.zhaofujun.nest.manager;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.zhaofujun.nest.NestConst;
import com.zhaofujun.nest.ddd.event.EventChannelProvider;

/**
 * 事件通道管理器
 */
public class EventChannelManager {

    private static Map<String, EventChannelProvider> providerMap = new HashMap<>();

    public static void addEventChannelProvider(EventChannelProvider... eventChannelProviders) {
        addCacheClient(Arrays.asList(eventChannelProviders));
    }

    public static void addCacheClient(Collection<EventChannelProvider> eventChannelProviders) {
        eventChannelProviders.forEach(cacheClient -> {
            providerMap.put(cacheClient.getCode(), cacheClient);
        });
    }

    public static EventChannelProvider getEventChannelProvider(String code) {
        EventChannelProvider channelProvider = providerMap.get(code);
        if (channelProvider == null)
            channelProvider = getEventChannelProvider(NestConst.defaultChannel);
        return channelProvider;
    }

    public static Collection<EventChannelProvider> getChannelProviders() {
        return providerMap.values();
    }
}
