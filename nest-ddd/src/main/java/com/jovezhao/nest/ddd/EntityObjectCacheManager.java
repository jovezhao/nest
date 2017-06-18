package com.jovezhao.nest.ddd;

import com.jovezhao.nest.cache.CacheClient;
import com.jovezhao.nest.cache.CacheFactory;
import com.jovezhao.nest.utils.SpringUtils;

/**
 * Created by Jove on 2016/8/31.
 */
 public class EntityObjectCacheManager {
    static {
        CacheFactory cacheFactory = new CacheFactory();
        cacheClient = cacheFactory.getCacheClient("entityObject");

    }

    private static String getCacheKey(EntityObject entityObject) {
        return entityObject.getClass().getName() + "_" + entityObject.getId();
    }

    private static String getCacheKey(Class clazz, String id) {
        return clazz.getName() + "_" + id;
    }

    private static CacheClient cacheClient;

    public static void put(EntityObject entityObject) {
        if (entityObject != null)
            cacheClient.put(getCacheKey(entityObject), entityObject);
    }

    public static void remove(EntityObject entityObject) {
        if (entityObject != null)
            cacheClient.remove(getCacheKey(entityObject));
    }

    public static <T extends EntityObject> T get(Class<T> tClass, String id) {
        return cacheClient.get(tClass, id);
    }
}
