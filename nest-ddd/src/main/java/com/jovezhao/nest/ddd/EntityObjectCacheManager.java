package com.jovezhao.nest.ddd;

import com.jovezhao.nest.cache.CacheContext;

/**
 * Created by Jove on 2016/8/31.
 */
 public class EntityObjectCacheManager {
    static {
        EntityObjectCacheManager.cacheContext = CacheContext.getContextByCode("entityObject");

    }

    private static String getCacheKey(EntityObject entityObject) {
        return entityObject.getClass().getName() + "_" + entityObject.getId();
    }

    private static String getCacheKey(Class clazz, String id) {
        return clazz.getName() + "_" + id;
    }

    private static CacheContext cacheContext;

    public static void put(EntityObject entityObject) {
        if (entityObject != null)
            cacheContext.put(getCacheKey(entityObject), entityObject);
    }

    public static void remove(EntityObject entityObject) {
        if (entityObject != null)
            cacheContext.remove(getCacheKey(entityObject));
    }

    public static <T extends EntityObject> T get(Class<T> tClass, String id) {
        return cacheContext.get(tClass, id);
    }
}
