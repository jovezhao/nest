package com.guoshouxiang.nest.context;

public class ServiceContextManager {
    private static ThreadLocal<ServiceContext> serviceContextThreadLocal = new ThreadLocal<>();

    public static void set(ServiceContext serviceContext) {
        serviceContextThreadLocal.set(serviceContext);
    }

    public static ServiceContext get() {
        return serviceContextThreadLocal.get();
    }
}
