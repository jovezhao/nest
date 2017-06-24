package com.jovezhao.nest.ddd.event;

import com.jovezhao.nest.ddd.event.provider.dft.DefaultEventChannelProvider;
import com.jovezhao.nest.utils.SpringUtils;
import org.apache.commons.collections.map.HashedMap;

import java.util.Map;
import java.util.Set;

/**
 * 事件配置管理器
 * Created by zhaofujun on 2017/6/21.
 */
public class EventConfigManager {

    private static Map<String, EventConfigItem> itemMap = new HashedMap();
    private static final String defaultCode = "default";

    static {

        // 添加SpringEventChannelProvider为默认通道
        EventConfigItem defaultItem = new EventConfigItem();
        defaultItem.setEventName(defaultCode);
        defaultItem.setChannelProvider(new DefaultEventChannelProvider());

        put(defaultItem);
        //通过spring ioc中获取所有EventChannelItem类型的bean来填充itemMap

        Set<EventConfigItem> eventConfigItems = SpringUtils.getInstances(EventConfigItem.class);
        for (EventConfigItem eventConfigItem : eventConfigItems) {
            itemMap.put(eventConfigItem.getEventName(), eventConfigItem);
        }

    }

    public static void put(EventConfigItem eventConfigItem) {
        itemMap.put(eventConfigItem.getEventName(), eventConfigItem);
    }

    public static void remove(String eventName) {
        itemMap.remove(eventName);
    }

    public static EventConfigItem get(String eventName) {
        EventConfigItem item = itemMap.get(eventName);
        if (item == null)
            item = itemMap.get(defaultCode);
        return item;
    }
}

