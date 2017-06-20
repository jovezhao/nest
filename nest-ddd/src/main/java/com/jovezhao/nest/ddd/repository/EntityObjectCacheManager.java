package com.jovezhao.nest.ddd.repository;

import com.jovezhao.nest.cache.CacheContext;
import com.jovezhao.nest.ddd.BaseEntityObject;
import com.jovezhao.nest.ddd.Identifier;

/**
 * Created by Jove on 2016/8/31.
 */
 public class EntityObjectCacheManager {
    static {
        cacheContext = CacheContext.getContextByCode("entityObject");
    }

    private static String getCacheKey(BaseEntityObject entityObject) {
        return entityObject.getClass().getName() + "_" + entityObject.getId();
    }

    private static String getCacheKey(Class clazz, String id) {
        return clazz.getName() + "_" + id;
    }

    private static CacheContext cacheContext;

    public static void put(BaseEntityObject entityObject) {
        if (entityObject != null)
            cacheContext.put(getCacheKey(entityObject), entityObject);
    }

    public static void remove(BaseEntityObject entityObject) {
        if (entityObject != null)
            cacheContext.remove(getCacheKey(entityObject));
    }

    public static <T extends BaseEntityObject> T get(Class<T> tClass, Identifier id) {
        return cacheContext.get(tClass, id.toString());
    }
}
