package com.zhaofujun.nest.springboot;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.zhaofujun.nest.provider.Container;

import java.util.Set;
import java.util.stream.Collectors;


@Component
public class SpringBeanContainer implements Container {
    public SpringBeanContainer(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    private ApplicationContext applicationContext;

    @Override
    public <T> Set<T> getInstances(Class<T> clazz) {
        return applicationContext.getBeansOfType(clazz).entrySet()
                .stream()
                .map(p -> p.getValue())
                .collect(Collectors.toSet());
    }


}
