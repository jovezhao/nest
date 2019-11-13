package com.zhaofujun.nest.container;

import com.zhaofujun.nest.core.BeanFinder;

import java.io.Serializable;

public interface ContainerProvider extends BeanFinder, Serializable {
    void initialize();
}
