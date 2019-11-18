package com.zhaofujun.nest.ioc.config;

import com.zhaofujun.nest.NestApplication;
import com.zhaofujun.nest.container.ContainerProvider;
import com.zhaofujun.nest.context.event.DefaultEventBus;
import com.zhaofujun.nest.core.EventBus;
import com.zhaofujun.nest.ioc.DefaultContainerProvider;

public class IocConfiguration {
    DefaultContainerProvider beanContainerProvider;

    public IocConfiguration(String prefix) {
        beanContainerProvider = new DefaultContainerProvider(prefix);
        NestApplication nestApplication = new NestApplication(beanContainerProvider);
        beanContainerProvider.register(DefaultContainerProvider.class, beanContainerProvider);
        beanContainerProvider.register(NestApplication.class, nestApplication);
        beanContainerProvider.register(EventBus.class, new DefaultEventBus(beanContainerProvider));
    }

    public void init() {


        beanContainerProvider.initialize();
    }

    public ContainerProvider getContainerProvider() {
        return beanContainerProvider;
    }
}
