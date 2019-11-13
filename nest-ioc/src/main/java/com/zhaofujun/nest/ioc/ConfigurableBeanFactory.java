package com.zhaofujun.nest.ioc;

import com.zhaofujun.nest.core.BeanFinder;

public interface ConfigurableBeanFactory extends BeanFinder {
    void register(Class clazz, Object value);
    void register(Class clazz, String name, Object value);
    void scan(String prefix);
}
