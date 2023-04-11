package com.zhaofujun.nest3.utils;

import com.zhaofujun.nest3.application.CacheClient;
import com.zhaofujun.nest3.application.NestApplication;
import com.zhaofujun.nest3.model.Entity;
import com.zhaofujun.nest3.model.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;


public class EntityUtils {
    //
    private static Logger logger = LoggerFactory.getLogger(EntityUtils.class);


    public static void setValue(Class clazz, Entity entityObject, String fieldName, Object value) {
        try {
            Field declaredField = clazz.getDeclaredField(fieldName);
            declaredField.setAccessible(true);
            declaredField.set(entityObject, value);
        } catch (Exception ex) {
            logger.warn("设置属性失败", ex);
        }
    }


    public static void setIdentifier(Entity entityObject, Identifier identifier) {
        setValue(Entity.class, entityObject, "id", identifier);
    }

    public static String getCacheKey(Entity entityObject) {
        return entityObject.getClassSimpleName() + "_" + entityObject.getId();
    }

    public static final String EntityCacheCode = "EntityCache";
    public static final String EntityCacheProviderCode = "EntityCacheProvider";
    public static final String EntityJsonProviderCode = "EntityJsonProvider";

    public static String getCacheKey(Class clazz, Identifier abstractIdentifier) {
        return clazz.getSimpleName() + "_" + abstractIdentifier;

    }

    public static CacheClient getEntityCacheClient( ) {
        CacheClient entityCache = NestApplication.current().getCacheClient(EntityUtils.EntityCacheCode);
        if (entityCache == null) {
            entityCache = CacheClient.newCacheClient("EntityCache", "EntityCacheProvider", 60 * 12, "EntityJsonProvider");
        }
        return entityCache;
    }


}