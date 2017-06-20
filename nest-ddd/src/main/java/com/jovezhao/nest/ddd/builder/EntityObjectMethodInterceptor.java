package com.jovezhao.nest.ddd.builder;

import com.jovezhao.nest.ddd.BaseEntityObject;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * Created by Jove on 2017/1/9.
 */


 class EntityObjectMethodInterceptor implements MethodInterceptor, Serializable {


    public Object getProxy(Class clazz) {
        return getProxy(clazz, null, null);
    }

    public Object getProxy(Class clazz, Class[] constructorArgTypes, Object[] constructorArgs) {
        Enhancer enhancer = new Enhancer();
        // 设置需要创建子类的类
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(this);

        if (constructorArgTypes == null || constructorArgs == null)
            return enhancer.create();
        else
            return enhancer.create(constructorArgTypes, constructorArgs);
    }

    // 实现MethodInterceptor接口方法
    public Object intercept(Object obj, Method method, Object[] args,
                            MethodProxy proxy) throws Throwable {

        Object result = proxy.invokeSuper(obj, args);

//
        String[] withoutMethod={
                "hashCode","equals","toString","notify","notifyAll","wait"
                ,"act","findRoles","delete"};

        if(method.getModifiers()==1 && !Arrays.asList(withoutMethod).contains(method.getName()) && !method.getName().startsWith("get") && !method.getName().startsWith("is")){
            Field field = BaseEntityObject.class.getDeclaredField("isLoad");
            field.setAccessible(true);
            if (!field.getBoolean(obj)) {
                Method method1 = BaseEntityObject.class.getDeclaredMethod("addToUnitOfWork");
                method1.setAccessible(true);
                method1.invoke(obj);
                System.out.println("调用"+method.getName());
            }
        }


        return result;
    }

}
