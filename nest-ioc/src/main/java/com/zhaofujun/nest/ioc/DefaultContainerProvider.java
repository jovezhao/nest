package com.zhaofujun.nest.ioc;

import com.zhaofujun.nest.container.ContainerProvider;

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
        beanContainer.staticInit();

    }

    public void register(Class clazz,Object value){
        beanContainer.register(clazz,value);
    }
}
