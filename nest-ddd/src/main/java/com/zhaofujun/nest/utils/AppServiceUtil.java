package com.zhaofujun.nest.utils;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import com.zhaofujun.nest.ddd.AppServiceIgnore;
import com.zhaofujun.nest.ddd.ApplicationService;
import com.zhaofujun.nest.ddd.context.MethodInvoker;
import com.zhaofujun.nest.ddd.context.ServiceMethodProcessor;
import com.zhaofujun.nest.ddd.context.Transaction;

public class AppServiceUtil {

    public static <T extends ApplicationService> T create(Class<T> tClass) throws InstantiationException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {

        Transaction transaction = new Transaction.DefaultTransaction();

        Enhancer enhancer = new Enhancer();
        // 设置需要创建子类的类
        enhancer.setSuperclass(tClass);
        enhancer.setCallback(new ApplicationServiceMethodInterceptor(transaction));
        Object t = enhancer.create();
        return (T) t;

    }

    private static class ApplicationServiceMethodInterceptor implements MethodInterceptor, Serializable {

        private class CglibMethodInvoker implements MethodInvoker {

            private MethodProxy methodProxy;
            private Object target;
            private Class targetClass;
            private Object[] args;
            private Method method;

            public CglibMethodInvoker(MethodProxy methodProxy, Method method, Object target, Class targetClass,
                    Object[] args) {
                this.methodProxy = methodProxy;
                this.method = method;
                this.target = target;
                this.targetClass = targetClass;
                this.args = args;
            }

            @Override
            public String getMethodName() {
                return this.method.getName();
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

            @Override
            public Method getMethod() {
                return this.method;
            }

        }

        private Transaction transaction;

        public ApplicationServiceMethodInterceptor(Transaction transaction) {
            this.transaction = transaction;
        }

        // 实现MethodInterceptor接口方法
        public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {

            AppServiceIgnore appServiceIgnore = method.getAnnotation(AppServiceIgnore.class);
            if (appServiceIgnore != null)
                return proxy.invokeSuper(obj, args);

            int modifiers = method.getModifiers();
            if (Modifier.isPublic(modifiers) && !Modifier.isStatic(modifiers)) {
                MethodInvoker methodInvoker = new CglibMethodInvoker(proxy, method, obj, obj.getClass(), args);
                ServiceMethodProcessor intercept = new ServiceMethodProcessor(methodInvoker, transaction);
                return intercept.doInvoke();
            } else {
                return proxy.invokeSuper(obj, args);
            }
        }

    }

}
