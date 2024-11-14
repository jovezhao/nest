package com.zhaofujun.nest.provider;

import java.util.Set;

public interface Container {
    <T> Set<T> getInstances(Class<T> clazz);

    // <T> T getInstance(Class<T> clazz);
}
