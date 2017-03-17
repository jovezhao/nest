package com.ywkj.nest.ddd.builder;

import com.ywkj.nest.ddd.EntityObject;


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



    public static <T extends EntityObject> T createForLoad(Class<T> tClass) {
        T t = create(tClass, (Class[]) null, (Object[]) null);
        beginLoad(t);
        return t;
    }

    public static void beginLoad(EntityObject entityObject) {

        try {
            Field field = EntityObject.class.getDeclaredField("isLoad");
            field.setAccessible(true);
            field.set(entityObject, true);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    public static void endLoad(EntityObject entityObject) {

        try {
            Field field = EntityObject.class.getDeclaredField("isLoad");
            field.setAccessible(true);
            field.set(entityObject, false);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }


    public static <T extends EntityObject, R> T create(Class<T> tClass, Consumer<T> function) {

        T t = createForLoad(tClass);
        function.accept(t);
        endLoad(t);
        return t;
    }
}
