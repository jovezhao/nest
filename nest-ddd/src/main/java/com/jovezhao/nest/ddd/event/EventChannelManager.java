package com.jovezhao.nest.ddd.event;

import com.jovezhao.nest.ddd.event.provider.dft.DefaultEventChannelProvider;
import com.jovezhao.nest.utils.SpringUtils;
import org.apache.commons.collections.map.HashedMap;

import java.util.Map;
import java.util.Set;

/**
 * 事件通道管理器
 * Created by zhaofujun on 2017/6/21.
 */
public class EventChannelManager {

    private static Map<String, EventChannelItem> itemMap = new HashedMap();
    private static final String defaultCode = "default";

    static {

        // 添加SpringEventChannelProvider为默认通道
        EventChannelItem defaultItem = new EventChannelItem();
        defaultItem.setEventName(defaultCode);
        defaultItem.setChannelProvider(new DefaultEventChannelProvider());

        put(defaultItem);
        //通过spring ioc中获取所有EventChannelItem类型的bean来填充itemMap

        Set<EventChannelItem> eventChannelItems = SpringUtils.getInstances(EventChannelItem.class);
        for (EventChannelItem eventChannelItem : eventChannelItems) {
            itemMap.put(eventChannelItem.getEventName(), eventChannelItem);
        }

    }

    public static void put(EventChannelItem eventChannelItem) {
        itemMap.put(eventChannelItem.getEventName(), eventChannelItem);
    }

    public static void remove(String eventName) {
        itemMap.remove(eventName);
    }

    public static EventChannelItem get(String eventName) {
        EventChannelItem item = itemMap.get(eventName);
        if (item == null)
            item = itemMap.get(defaultCode);
        return item;
    }
}

