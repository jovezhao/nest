package com.zhaofujun.nest.utils;

import com.zhaofujun.nest.context.model.BaseEntity;
import com.zhaofujun.nest.context.model.Identifier;
import com.zhaofujun.nest.SystemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;


///**
// * 实体工具
// * Created by Jove on 2017/1/9.
// */
public class EntityUtils {
    //
    private static Logger logger = LoggerFactory.getLogger(EntityUtils.class);

    public static void setValue(Class clazz, BaseEntity entityObject, String fieldName, Object value) {
        try {
            Field declaredField = clazz.getDeclaredField(fieldName);
            declaredField.setAccessible(true);
            declaredField.set(entityObject, value);
        } catch (Exception ex) {
            logger.warn("设置属性失败", ex);
        }
    }


    /**
     * 修改实体为加载状态，加载状态的实体属性发生变化时不提交到工作单元
     *
     * @param entityObject
     */
    public static void load(BaseEntity entityObject) {

        try {
            Field field = BaseEntity.class.getDeclaredField("_loading");
            field.setAccessible(true);
            field.set(entityObject, true);
        } catch (Exception e) {
            throw new SystemException("更新实体加载状态失败", e);
        }

    }

    /**
     * 修改实体为加载完成状态，完成状态的实体属性发生变化时自将提交到工作单元
     *
     * @param entityObject
     */
    public static void endLoad(BaseEntity entityObject) {

        try {
            Field field = BaseEntity.class.getDeclaredField("_loading");
            field.setAccessible(true);
            field.set(entityObject, false);
        } catch (Exception e) {
            throw new SystemException("完成实体加载状态失败", e);
        }

    }

    private static boolean isLoading(BaseEntity entityObject) {
        try {
            Field field = BaseEntity.class.getDeclaredField("_loading");
            field.setAccessible(true);
            return field.getBoolean(entityObject);
        } catch (Exception e) {
            return false;
        }
    }

    public static void represent(BaseEntity entityObject, boolean v) {
        try {
            Field field = BaseEntity.class.getDeclaredField("_represented");
            field.setAccessible(true);
            field.set(entityObject, v);
        } catch (Exception e) {
            throw new SystemException("完成实体加载状态失败", e);
        }
    }

    public static void setIdentifier(BaseEntity entityObject, Identifier identifier) {
        try {
            Field field = BaseEntity.class.getDeclaredField("id");
            field.setAccessible(true);
            field.set(entityObject, identifier);
        } catch (Exception e) {
            throw new SystemException("设置实体标识失败", e);
        }
    }

    public static boolean isRepresented(BaseEntity entityObject) {
        try {
            Field field = BaseEntity.class.getDeclaredField("_represented");
            field.setAccessible(true);
            return field.getBoolean(entityObject);
        } catch (Exception e) {
            return false;
        }
    }
}