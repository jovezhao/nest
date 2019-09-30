package com.zhaofujun.nest.ioc;

import com.zhaofujun.nest.context.ServiceContext;
import com.zhaofujun.nest.SystemException;
import com.zhaofujun.nest.ioc.annotation.Service;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Arrays;

public class ApplicationServiceInterceptor implements MethodInterceptor, Serializable {

    private BeanContainer beanContainer;

    public ApplicationServiceInterceptor(BeanContainer beanContainer) {
        this.beanContainer = beanContainer;
    }

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    // 实现MethodInterceptor接口方法
    public Object intercept(Object obj, Method method, Object[] args,
                            MethodProxy proxy) throws Throwable {
        String[] withoutMethod = {
                "hashCode", "equals", "toString", "notify", "notifyAll", "wait"};
        boolean tagProxy = method.getModifiers() == 1 &&
                !Arrays.asList(withoutMethod).contains(method.getName()) &&
                obj.getClass().isAnnotationPresent(Service.class);

        ServiceContext serviceContext = null;
        if (tagProxy) {
            //执行之前
            serviceContext = ServiceContext.newInstance(method.getDeclaringClass(), beanContainer);
        }

        Object result = null;
        try {
            result = proxy.invokeSuper(obj, args);
        } catch (SystemException ex) {

        }
        if (tagProxy) {
            //执行之后提交事务
            serviceContext.getContextUnitOfWork().commit();
        }

        return result;
    }

}
