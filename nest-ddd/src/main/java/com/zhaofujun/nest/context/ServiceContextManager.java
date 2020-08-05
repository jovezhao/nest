package com.zhaofujun.nest.context;

import com.alibaba.ttl.TransmittableThreadLocal;

import java.util.Stack;

public class ServiceContextManager {

    //使用堆栈来处理当前上下文信息

    private static TransmittableThreadLocal<Stack<ServiceContext>> serviceContextThreadLocal = new TransmittableThreadLocal<>();

    public static void set(ServiceContext serviceContext) {
        Stack<ServiceContext> serviceContexts = serviceContextThreadLocal.get();
        if (serviceContexts == null) {
            serviceContexts = new Stack<>();
            serviceContextThreadLocal.set(serviceContexts);
        }
        serviceContexts.push(serviceContext);
    }

    public static ServiceContext getCurrent() {
        Stack<ServiceContext> serviceContexts = serviceContextThreadLocal.get();
        if(serviceContexts==null) return null;
        return serviceContexts.peek();
    }

    public static ServiceContext pop() {
        Stack<ServiceContext> serviceContexts = serviceContextThreadLocal.get();
        return serviceContexts.pop();
    }
}
