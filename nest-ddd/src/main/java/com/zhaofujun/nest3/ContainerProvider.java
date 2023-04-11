package com.zhaofujun.nest3;

import java.io.Serializable;
import java.util.Set;

public interface ContainerProvider extends Serializable {
    <T> Set<T> getInstances(Class<T> clazz);
}
