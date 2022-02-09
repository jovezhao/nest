package com.zhaofujun.nest.context.appservice;

import com.alibaba.ttl.TransmittableThreadLocal;

import java.util.Stack;
import java.util.concurrent.ConcurrentHashMap;

public class ServiceContextManager {

    /**
     * 使用堆栈来处理当前上下文信息
     */
    private static TransmittableThreadLocal<ConcurrentHashMap<String, Stack<ServiceContext>>> serviceContextThreadLocal = new TransmittableThreadLocal<>();

    /**
     * 用来记录主线程名称,主要用于应用服务开启多线程调用领域服务方法，通过记录的主线程名来获取主线程的上下文，用于提交领域服务的事务
     */
    private static TransmittableThreadLocal<String> mainNameThreadLocal = new TransmittableThreadLocal<>();


    public static void set(ServiceContext serviceContext) {
        ConcurrentHashMap<String, Stack<ServiceContext>> serviceContextHashMap = serviceContextThreadLocal.get();
        if (serviceContextHashMap == null) {
            serviceContextHashMap = new ConcurrentHashMap<>();
            serviceContextThreadLocal.set(serviceContextHashMap);
        }
        if (serviceContextHashMap.isEmpty()) {
            mainNameThreadLocal.set(Thread.currentThread().getName());
        }
        Stack<ServiceContext> serviceContextStack = serviceContextHashMap.get(Thread.currentThread().getName());
        if (serviceContextStack == null) {
            serviceContextStack = new Stack<>();
        }
        serviceContextStack.push(serviceContext);
        serviceContextHashMap.put(Thread.currentThread().getName(), serviceContextStack);
    }

    public static ServiceContext get() {
        Stack<ServiceContext> serviceContextStack = getStack();
        if (serviceContextStack == null || serviceContextStack.empty()) {
            return null;
        }
        return serviceContextStack.peek();
    }

    public static Stack<ServiceContext> getStack() {
        ConcurrentHashMap<String, Stack<ServiceContext>> serviceContextHashMap = serviceContextThreadLocal.get();
        if (serviceContextHashMap == null || serviceContextHashMap.isEmpty()) {
            return null;
        }
        Stack<ServiceContext> serviceContextStack = serviceContextHashMap.get(Thread.currentThread().getName());
        if (serviceContextStack == null || serviceContextStack.empty()) {
            serviceContextStack = serviceContextHashMap.get(mainNameThreadLocal.get());
        }
        return serviceContextStack;
    }

    public static void pop() {
        ConcurrentHashMap<String, Stack<ServiceContext>> serviceContextHashMap = serviceContextThreadLocal.get();
        if (serviceContextHashMap == null || serviceContextHashMap.isEmpty()) {
            serviceContextThreadLocal.remove();
            mainNameThreadLocal.remove();
            return;
        }
        Stack<ServiceContext> serviceContextStack = serviceContextHashMap.get(Thread.currentThread().getName());
        if (serviceContextStack != null && !serviceContextStack.isEmpty()) {
            serviceContextStack.pop();
        }
        if (serviceContextStack == null || serviceContextStack.isEmpty()) {
            serviceContextHashMap.remove(Thread.currentThread().getName());
        }
        if (serviceContextHashMap.isEmpty()) {
            serviceContextThreadLocal.remove();
            mainNameThreadLocal.remove();
        }
    }


}
