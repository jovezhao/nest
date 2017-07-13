package com.jovezhao.nest.ddd.repository;

import com.jovezhao.nest.cache.CacheContext;
import com.jovezhao.nest.ddd.BaseEntityObject;
import com.jovezhao.nest.ddd.Identifier;

/**
 * 实体缓存管理器
 * Created by Jove on 2016/8/31.
 */
public class EntityCacheManager {


    private static String getCacheKey(BaseEntityObject entityObject) {
        return entityObject.getClass().getName() + "_" + entityObject.getId();
    }

    private static String getCacheKey(Class clazz, String id) {
        return clazz.getName() + "_" + id;
    }

    private static CacheContext cacheContext;

    public synchronized static CacheContext getCacheContext() {
        if (cacheContext == null) {
            cacheContext = CacheContext.getContextByCode("entityObject");
        }
        return cacheContext;
    }

    public static void put(BaseEntityObject entityObject) {
        if (entityObject != null)
            getCacheContext().put(getCacheKey(entityObject), entityObject);
    }

    public static void remove(BaseEntityObject entityObject) {
        if (entityObject != null)
            getCacheContext().remove(getCacheKey(entityObject));
    }
    public static void removeAll(){
        getCacheContext().removeAll();
    }

    public static <T extends BaseEntityObject> T get(Class<T> tClass, Identifier id) {
        return getCacheContext().get(tClass, id.toString());
    }
}
