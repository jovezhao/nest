package com.jovezhao.nest.ddd.builder;

import com.jovezhao.nest.ddd.BaseEntityObject;

import java.lang.reflect.Field;
import java.util.function.Consumer;

/**
 * 实体工厂
 * Created by Jove on 2017/1/9.
 */
 class EntityObjectFactory {
    public static <T> T create(Class<T> tClass) {
        return create(tClass, null, null);
    }

    public static <T> T create(Class<T> tClass, Class[] constructorArgTypes, Object[] constructorArgs) {
        EntityObjectMethodInterceptor proxy = new EntityObjectMethodInterceptor();
        T proxyImp = (T) proxy.getProxy(tClass, constructorArgTypes, constructorArgs);
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
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    public static void endLoad(BaseEntityObject entityObject) {

        try {
            Field field = BaseEntityObject.class.getDeclaredField("isLoad");
            field.setAccessible(true);
            field.set(entityObject, false);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }


    public static <T extends BaseEntityObject, R> T create(Class<T> tClass, Consumer<T> function) {

        T t = createForLoad(tClass);
        function.accept(t);
        endLoad(t);
        return t;
    }
}
