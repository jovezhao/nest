package com.ywkj.nest.ddd;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by Jove on 2017/1/9.
 */
public class EntityObjectMethodInterceptor implements MethodInterceptor {

    private Enhancer enhancer = new Enhancer();

    public Object getProxy(Class clazz) {
       return getProxy(clazz,null,null);
    }

    public Object getProxy(Class clazz, Class[] constructorArgTypes, Object[] constructorArgs) {
        // 设置需要创建子类的类
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(this);
        return enhancer.create(constructorArgTypes, constructorArgs);
    }

    // 实现MethodInterceptor接口方法
    public Object intercept(Object obj, Method method, Object[] args,
                            MethodProxy proxy) throws Throwable {

        Object result = proxy.invokeSuper(obj, args);

        if (method.getName().startsWith("set")) {
            EntityObject entityObject = (EntityObject) obj;
            entityObject.save();
        }
        return result;
    }

}
