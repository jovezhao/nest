package com.ywkj.nest.ddd;

/**
 * 实体工厂
 * Created by Jove on 2017/1/9.
 */
public class EntityObjectFactory {
    public static <T> T create(Class<T> tClass) {
        return create(tClass, null, null);
    }

    public static <T> T create(Class<T> tClass, Class[] constructorArgTypes, Object[] constructorArgs) {
        EntityObjectMethodInterceptor proxy = new EntityObjectMethodInterceptor();
        T proxyImp = (T) proxy.getProxy(tClass, constructorArgTypes, constructorArgs);
        return proxyImp;
    }
}
