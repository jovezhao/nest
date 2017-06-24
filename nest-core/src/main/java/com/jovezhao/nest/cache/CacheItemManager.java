package com.jovezhao.nest.cache;

import com.jovezhao.nest.exception.SystemException;
import com.jovezhao.nest.log.Log;
import com.jovezhao.nest.log.LogAdapter;
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
    private static Log logger = new LogAdapter(CacheItemManager.class);

    static {
        //通过spring ioc中获取所有cacheItem类型的bean来填充itemMap

        CacheItem defaultItem = new CacheItem();
        defaultItem.setName("默认配置");
        defaultItem.setCoce(defaultCode);
        defaultItem.setIdleSeconds(60 * 1000);
        defaultItem.setProvider(new DefaultCacheProvider());

        put(defaultItem);

        try {
            Set<CacheItem> cacheItems = SpringUtils.getInstances(CacheItem.class);
            for (CacheItem cacheItem : cacheItems) {
                itemMap.put(cacheItem.getCoce(), cacheItem);
            }
        } catch (SystemException ex) {
            //获取bean异常或没有在spring环境中时，不配置bean
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
        if (item == null) {
            logger.debug("没有找到名{}的缓存项，将采用默认的缓存配置", cacheCode);
            item = itemMap.get(defaultCode);
        }
        return item;
    }
}
