package com.jovezhao.nest.ddd.builder;

import com.jovezhao.nest.ddd.BaseEntityObject;
import com.jovezhao.nest.log.Log;
import com.jovezhao.nest.log.LogAdapter;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Locale;

/**
 * Created by Jove on 2017/1/9.
 */


/**
 * 实体方法拦截器
 */
 class EntityObjectMethodInterceptor implements MethodInterceptor, Serializable {

     private Log log=new LogAdapter(this.getClass());
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
                log.debug("领域实体发生更改，调用方法{}",method.getName());
            }
        }


        return result;
    }

}
