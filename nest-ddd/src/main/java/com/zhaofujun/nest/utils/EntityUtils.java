package com.zhaofujun.nest.utils;

import com.zhaofujun.nest.context.loader.EntityMethodInterceptor;
import com.zhaofujun.nest.context.model.Entity;
import com.zhaofujun.nest.context.model.Role;
import com.zhaofujun.nest.core.Identifier;
import com.zhaofujun.nest.SystemException;
import net.sf.cglib.proxy.Enhancer;
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


    public static <T extends Entity> T create(Class<T> tClass, Identifier identifier, boolean newInstance, boolean loading) {

        if (tClass.isAssignableFrom(Role.class))
            throw new SystemException("角色不能直接通过实体加载器创建，请通过实体act方法扮演");
        Enhancer enhancer = new Enhancer();
        // 设置需要创建子类的类
        enhancer.setSuperclass(tClass);
        enhancer.setCallback(new EntityMethodInterceptor());
        T t = (T) enhancer.create();
        EntityUtils.represent(t, true);// 被代理的对象默认设置代理为true
        EntityUtils.setIdentifier(t, identifier);
        EntityUtils.setNewInstance(t, newInstance);
        EntityUtils.setLoading(t, loading);
        return t;
    }


    public static void setValue(Class clazz, Entity entityObject, String fieldName, Object value) {
        try {
            Field declaredField = clazz.getDeclaredField(fieldName);
            declaredField.setAccessible(true);
            declaredField.set(entityObject, value);
        } catch (Exception ex) {
            logger.warn("设置属性失败", ex);
        }
    }


    //修改实体为加载状态，加载状态的实体属性发生变化时不提交到工作单元
    public static void setLoading(Entity entityObject,boolean loading) {

        try {
            Field field = Entity.class.getDeclaredField("_loading");
            field.setAccessible(true);
            field.set(entityObject, loading);
        } catch (Exception e) {
            throw new SystemException("更新实体加载状态失败", e);
        }

    }

    public static boolean isLoading(Entity entityObject) {
        try {
            Field field = Entity.class.getDeclaredField("_loading");
            field.setAccessible(true);
            return field.getBoolean(entityObject);
        } catch (Exception e) {
            return false;
        }
    }

    public static void represent(Entity entityObject, boolean v) {
        try {
            Field field = Entity.class.getDeclaredField("_represented");
            field.setAccessible(true);
            field.set(entityObject, v);
        } catch (Exception e) {
            throw new SystemException("完成实体加载状态失败", e);
        }
    }

    public static void setIdentifier(Entity entityObject, Identifier identifier) {
        try {
            Field field = Entity.class.getDeclaredField("id");
            field.setAccessible(true);
            field.set(entityObject, identifier);
        } catch (Exception e) {
            throw new SystemException("设置实体标识失败", e);
        }
    }

    public static boolean isRepresented(Entity entityObject) {
        try {
            Field field = Entity.class.getDeclaredField("_represented");
            field.setAccessible(true);
            return field.getBoolean(entityObject);
        } catch (Exception e) {
            return false;
        }
    }
    public static boolean isNewInstance(Entity entityObject) {
        try {
            Field field = Entity.class.getDeclaredField("_newInstance");
            field.setAccessible(true);
            return field.getBoolean(entityObject);
        } catch (Exception e) {
            return false;
        }
    }
    public static void setNewInstance(Entity entityObject,boolean newInstance) {
        try {
            Field field = Entity.class.getDeclaredField("_newInstance");
            field.setAccessible(true);
            field.set(entityObject, newInstance);
        } catch (Exception e) {
            throw new SystemException("设置实体是否为新实例失败", e);
        }
    }

}