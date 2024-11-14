package com.zhaofujun.nest.ddd.context;

import com.alibaba.ttl.TransmittableThreadLocal;

/**
 * 服务上下文管理器
 */
public class ServiceContextManager {

    private static TransmittableThreadLocal<ServiceContextBox> threadLocal = new TransmittableThreadLocal<>();

    public static ServiceContext newServiceContext(MethodInvoker methodInvoker) {
        ServiceContext serviceContext = new ServiceContext();
        ServiceContextBox serviceContextBox = threadLocal.get();
        if (serviceContextBox == null) {
            serviceContextBox = new ServiceContextBox(serviceContext, null);
            threadLocal.set(serviceContextBox);
        } else {
            if (serviceContextBox.owner()) {
                serviceContextBox.push(serviceContext);
            } else {
                ServiceContextBox newBox = new ServiceContextBox(serviceContext, serviceContextBox);
                threadLocal.set(newBox);
            }
        }
        return serviceContext;
    }

    public static ServiceContext getCurrentContext() {
        ServiceContextBox serviceContextBox = threadLocal.get();
        if (serviceContextBox == null)
            return null;
        return serviceContextBox.get();
    }

    public static void pop() {
        ServiceContextBox serviceContextBox = threadLocal.get();
        if (serviceContextBox == null)
            return;
        if (serviceContextBox.owner()) {
            if (serviceContextBox.onlyOne()) {
                threadLocal.set(serviceContextBox.getParent());
            } else {
                serviceContextBox.pop();
            }
        }
    }
}
