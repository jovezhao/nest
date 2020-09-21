package com.zhaofujun.nest.context.appservice;

import com.zhaofujun.nest.standard.AppService;
import com.zhaofujun.nest.standard.SystemException;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class ApplicationServiceCreator {

    public static <T> T create(Class<T> tClass) {
        if (tClass.isAnnotationPresent(AppService.class)) {
            Enhancer enhancer = new Enhancer();
            // 设置需要创建子类的类
            enhancer.setSuperclass(tClass);
            enhancer.setCallback(new ApplicationServiceMethodInterceptor());
            Object t = enhancer.create();
            return (T) t;
        } else {
            throw new SystemException("类型" + tClass + "不是有效的应用服务");
        }
    }


    private static class ApplicationServiceMethodInterceptor implements MethodInterceptor, Serializable {

        private class CglibMethodInvoker implements MethodInvoker {

            private MethodProxy methodProxy;
            private String methodName;
            private Object target;
            private Class targetClass;
            private Object[] args;

            public CglibMethodInvoker(MethodProxy methodProxy, String methodName, Object target, Class targetClass, Object[] args) {
                this.methodProxy = methodProxy;
                this.methodName = methodName;
                this.target = target;
                this.targetClass = targetClass;
                this.args = args;
            }

            @Override
            public String getMethodName() {
                return this.methodName;
            }

            @Override
            public Object invoke() throws Throwable {
                return methodProxy.invokeSuper(target, args);
            }

            @Override
            public Class getTargetClass() {
                return this.targetClass;
            }

            @Override
            public Object getTarget() {
                return this.target;
            }
        }

        // 实现MethodInterceptor接口方法
        public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {

            int modifiers = method.getModifiers();
            if (Modifier.isPublic(modifiers) && !Modifier.isStatic(modifiers)) {
                MethodInvoker methodInvoker = new CglibMethodInvoker(proxy, method.getName(), obj, obj.getClass(), args);
                ApplicationServiceIntercept intercept = new ApplicationServiceIntercept(methodInvoker, null);
                return intercept.doInvoke();
            } else {
                return proxy.invokeSuper(obj, args);
            }
        }

    }

}
