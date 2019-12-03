package com.zhaofujun.nest.context.loader;


import com.zhaofujun.nest.context.model.Entity;
import com.zhaofujun.nest.utils.EntityUtils;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

public class EntityMethodInterceptor implements MethodInterceptor, Serializable {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    // 实现MethodInterceptor接口方法
    public Object intercept(Object obj, Method method, Object[] args,
                            MethodProxy proxy) throws Throwable {

        Object result = proxy.invokeSuper(obj, args);

//
        String[] withoutMethod = {
                "hashCode", "equals", "toString", "notify", "notifyAll", "wait"
                , "act", "verify", "findRoles", "delete"};

        if (method.getModifiers() == 1 && !Arrays.asList(withoutMethod).contains(method.getName()) && !method.getName().startsWith("get") && !method.getName().startsWith("is")) {
            Entity entity = (Entity) obj;
            if (!EntityUtils.isLoading(entity)) {
                EntityUtils.setChanged(entity, true);
                //调用实体验证方法
                entity.verify();
                logger.debug("领域实体发生更改，调用方法{}", method.getName());
            }
        }


        return result;
    }

}