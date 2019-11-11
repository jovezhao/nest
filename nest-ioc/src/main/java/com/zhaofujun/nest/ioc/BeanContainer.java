package com.zhaofujun.nest.ioc;

public interface BeanContainer extends ConfigurableBeanFactory {
    void staticInit();
}
