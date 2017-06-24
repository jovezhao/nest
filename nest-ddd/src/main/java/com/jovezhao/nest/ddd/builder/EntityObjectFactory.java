package com.jovezhao.nest.ddd.builder;

import com.jovezhao.nest.ddd.BaseEntityObject;
import com.jovezhao.nest.exception.SystemException;
import org.springframework.cglib.proxy.Enhancer;

import java.lang.reflect.Field;
import java.util.function.Consumer;

/**
 * 实体工厂
 * Created by Jove on 2017/1/9.
 */
class EntityObjectFactory {
    public static <T extends BaseEntityObject> T create(Class<T> tClass) {
        return create(tClass, null, null);
    }

    public static <T extends BaseEntityObject> T create(Class<T> tClass, Class[] constructorArgTypes, Object[] constructorArgs) {
        T proxyImp = newInstance(tClass, constructorArgTypes, constructorArgs);
        return proxyImp;
    }


    public static <T extends BaseEntityObject> T createForLoad(Class<T> tClass) {
        T t = create(tClass, (Class[]) null, (Object[]) null);
        beginLoad(t);
        return t;
    }

    public static void beginLoad(BaseEntityObject entityObject) {

        try {
            Field field = BaseEntityObject.class.getDeclaredField("isLoad");
            field.setAccessible(true);
            field.set(entityObject, true);
        } catch (Exception e) {
            throw new SystemException("更新实体加载状态失败", e);
        }

    }

    public static void endLoad(BaseEntityObject entityObject) {

        try {
            Field field = BaseEntityObject.class.getDeclaredField("isLoad");
            field.setAccessible(true);
            field.set(entityObject, false);
        } catch (Exception e) {
            throw new SystemException("完成实体加载状态失败", e);
        }

    }


    /**
     * 使用cglib创建一个实体对象，并且监控对象的属性变化
     *
     * @param tClass
     * @param constructorArgTypes
     * @param constructorArgs
     * @param <T>
     * @return
     */
    private static <T extends BaseEntityObject> T newInstance(Class<T> tClass, Class[] constructorArgTypes, Object[] constructorArgs) {
        Enhancer enhancer = new Enhancer();
        // 设置需要创建子类的类
        enhancer.setSuperclass(tClass);
        enhancer.setCallback(new EntityObjectMethodInterceptor());

        if (constructorArgTypes == null || constructorArgs == null)
            return (T) enhancer.create();
        else
            return (T) enhancer.create(constructorArgTypes, constructorArgs);
    }
}
