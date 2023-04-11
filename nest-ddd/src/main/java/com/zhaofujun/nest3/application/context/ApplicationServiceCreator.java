package com.zhaofujun.nest3.application.context;

import com.zhaofujun.nest3.SystemException;
import com.zhaofujun.nest3.model.AppService;
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
            private Object target;
            private Class targetClass;
            private Object[] args;
            private Method method;

            public CglibMethodInvoker(MethodProxy methodProxy, Method method, Object target, Class targetClass, Object[] args) {
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


        // 实现MethodInterceptor接口方法
        public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {

            int modifiers = method.getModifiers();
            if (Modifier.isPublic(modifiers) && !Modifier.isStatic(modifiers)) {
                MethodInvoker methodInvoker = new CglibMethodInvoker(proxy, method, obj, obj.getClass(), args);
                ApplicationServiceIntercept intercept = new ApplicationServiceIntercept(methodInvoker, new TransactionManager() {
                    @Override
                    public void commit(Runnable runnable) {
                        runnable.run();
                    }
                });
                return intercept.doInvoke();
            } else {
                return proxy.invokeSuper(obj, args);
            }
        }

    }

}
