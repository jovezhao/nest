package com.zhaofujun.nest.context;

import com.alibaba.ttl.TransmittableThreadLocal;

public class ServiceContextManager {
    private static TransmittableThreadLocal<ServiceContext> serviceContextThreadLocal = new TransmittableThreadLocal<>();

    public static void set(ServiceContext serviceContext) {
        serviceContextThreadLocal.set(serviceContext);
    }

    public static ServiceContext get() {
        return serviceContextThreadLocal.get();
    }
}
