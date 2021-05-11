package com.zhaofujun.nest.utils;

import com.zhaofujun.nest.standard.Entity;
import com.zhaofujun.nest.standard.Identifier;

/**
 * 实体缓存管理器
 */
public class EntityCacheUtils {

    public static String getCacheKey(Entity entityObject) {
        return entityObject.getClassSimpleName() + "_" + entityObject.getId();
    }

    public static String getCacheKey(Class clazz, Identifier abstractIdentifier) {
        return clazz.getSimpleName() + "_" + abstractIdentifier;

    }

    public static final String getCacheCode() {
        return "ENTITY_CACHE";
    }


}
