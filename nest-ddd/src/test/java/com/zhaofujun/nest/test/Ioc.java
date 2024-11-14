package com.zhaofujun.nest.test;

import java.util.Set;

import com.zhaofujun.nest.provider.Container;

public class Ioc implements Container {
    

    @Override
    public <T> Set<T> getInstances(Class<T> clazz) {
        return null;
    }

}
