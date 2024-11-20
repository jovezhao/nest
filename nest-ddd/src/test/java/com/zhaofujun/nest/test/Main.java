package com.zhaofujun.nest.test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

class CallExample {
    public int add(int a, int b) {
        return a + b;
    }
}

public class Main {
    public static void main(String[] args) throws NoSuchMethodException, SecurityException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {
        // CallExample example = new CallExample();
        // long startTime = System.currentTimeMillis();
        // for (int i = 0; i < 1000000; i++) {
        // example.add(i, i);
        // }
        // long endTime = System.currentTimeMillis();
        // System.out.println("直接方法调用耗时：" + (endTime - startTime) + "毫秒");

        // Class<?> clazz = example.getClass();
        // Method method = clazz.getMethod("add", int.class, int.class);
        // startTime = System.currentTimeMillis();
        // for (int i = 0; i < 1000000; i++) {
        // method.invoke(example, i, i);
        // }
        // endTime = System.currentTimeMillis();
        // System.out.println("反射方法调用耗时：" + (endTime - startTime) + "毫秒");
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(CallExample.class);
        enhancer.setCallback(new MethodInterceptor() {
            @Override
            public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
                return proxy.invokeSuper(obj, args);
            }
        });
        CallExample example1 = (CallExample) enhancer.create();
        CallExample example2 = new CallExample();
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            example1.add(i, i);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("代理方法调用耗时：" + (endTime - startTime) + "毫秒");
        startTime = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            example2.add(i, i);
        }
        endTime = System.currentTimeMillis();
        System.out.println("直接方法调用耗时：" + (endTime - startTime) + "毫秒");
    }
}