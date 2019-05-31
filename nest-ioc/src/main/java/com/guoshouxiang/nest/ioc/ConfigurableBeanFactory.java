package com.guoshouxiang.nest.ioc;

import com.guoshouxiang.nest.container.BeanFinder;

public interface ConfigurableBeanFactory extends BeanFinder {
    void register(Class clazz, Object value);
    void register(Class clazz, String name, Object value);
    void scan(String prefix);
}
