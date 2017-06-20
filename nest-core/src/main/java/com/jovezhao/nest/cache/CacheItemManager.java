package com.jovezhao.nest.cache;

import com.jovezhao.nest.utils.SpringUtils;
import org.apache.commons.collections.map.HashedMap;

import java.util.Map;
import java.util.Set;

/**
 * Created by zhaofujun on 2017/6/20.
 */
public class CacheItemManager {
    private static Map<String, CacheItem> itemMap = new HashedMap();
    private static final String defaultCode = "default";

    static {
        //通过spring ioc中获取所有cacheItem类型的bean来填充itemMap

        CacheItem defaultItem = new CacheItem();
        defaultItem.setName("默认配置");
        defaultItem.setCoce(defaultCode);
        defaultItem.setIdleSeconds(60 * 1000);
        defaultItem.setProvider(new DefaultCacheProvider());

        put(defaultItem);

        Set<CacheItem> cacheItems = SpringUtils.getInstances(CacheItem.class);
        for (CacheItem cacheItem : cacheItems) {
            itemMap.put(cacheItem.getCoce(), cacheItem);
        }

    }

    public static void put(CacheItem cacheItem) {
        itemMap.put(cacheItem.getCoce(), cacheItem);
    }

    public static void remove(String cacheCode) {
        itemMap.remove(cacheCode);
    }

    public static CacheItem get(String cacheCode) {
        CacheItem item = itemMap.get(cacheCode);
        if (item == null)
            item = itemMap.get(defaultCode);
        return item;
    }
}
