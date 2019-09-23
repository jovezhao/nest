package com.zhaofujun.nest.container;

import java.io.Serializable;

public interface ContainerProvider extends BeanFinder , Serializable {
    void initialize();

}
