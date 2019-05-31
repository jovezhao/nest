package com.guoshouxiang.nest.ioc;

import com.guoshouxiang.nest.NestApplication;
import com.guoshouxiang.nest.container.ContainerProvider;
import com.guoshouxiang.nest.context.event.EventBus;

import java.util.*;

public class DefaultContainerProvider implements ContainerProvider {
    private BeanContainer beanContainer;

    public DefaultContainerProvider(String prefix) {
        this.beanContainer = new DefaultBeanContainer();
        this.beanContainer.scan(prefix);
    }

    @Override
    public <T> T getInstance(Class<T> clazz) {
        return beanContainer.getInstance(clazz);
    }

    @Override
    public <T> T getInstance(Class<T> clazz, String name) {
        return beanContainer.getInstance(clazz, name);
    }

    @Override
    public <T> Set<T> getInstances(Class<T> clazz) {
        return beanContainer.getInstances(clazz);
    }

    @Override
    public void initialize() {
        beanContainer.register(DefaultContainerProvider.class, this);
        beanContainer.register(EventBus.class, new EventBus(this));
        beanContainer.register(NestApplication.class, this);

        beanContainer.initialize();

    }
}
