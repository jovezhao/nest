package com.zhaofujun.nest.utils;

import com.zhaofujun.nest.context.model.Entity;
import com.zhaofujun.nest.core.Identifier;

/**
 * 实体缓存管理器
 */
public class EntityCacheUtils {


    public static final String PROXY_SPLIT_STR = "$";
    public static final int BEGIN_INDEX = 0;

    public static String getCacheKey(Entity entityObject) {
        String className = entityObject.getClass().getSimpleName();
        int endIndex = className.indexOf(PROXY_SPLIT_STR);
        return className.substring(BEGIN_INDEX, endIndex) + "_" + entityObject.getId();
    }

    public static String getCacheKey(Class clazz, Identifier identifier) {
        return clazz.getSimpleName() + "_" + identifier;

    }
    public static String getCacheCode(){
        return "ENTITY_CACHE";
    }

//
//    private static CacheClient iCacheClient;
//
//    public synchronized static CacheClient getCacheClient() {
//        if (iCacheClient == null) {
//            iCacheClient = CacheClient.getCacheClientByCode("entityObject");
//        }
//        return iCacheClient;
//    }
//
//    public static void put(Entity entityObject) {
//        if (entityObject != null) {
//            getCacheClient().put(getCacheKey(entityObject), entityObject);
//        }
//    }
//
//    public static <T extends Entity> T get(Class<T> tClass, Identifier identifier) {
//        String key = tClass.getSimpleName() + " _" + identifier;
//        return getCacheClient().get(tClass, key);
//    }
//
//    public static void remove(Entity entityObject) {
//        if (entityObject != null)
//            getCacheClient().remove(getCacheKey(entityObject));
//    }
//
//    public static void removeAll() {
//        getCacheClient().removeAll();
//    }

}
