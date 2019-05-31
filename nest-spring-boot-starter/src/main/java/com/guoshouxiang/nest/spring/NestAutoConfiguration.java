package com.guoshouxiang.nest.spring;

import com.guoshouxiang.nest.NestApplication;
import com.guoshouxiang.nest.container.BeanFinder;
import com.guoshouxiang.nest.container.ContainerProvider;
import com.guoshouxiang.nest.context.event.EventBus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NestAutoConfiguration {
    @Bean
    public SpringBeanContainerProvider getSpringBeanContainerProvider() {
        return new SpringBeanContainerProvider();
    }

    @Bean
    public NestAspect getNestAspect() {
        return new NestAspect();
    }

    @Bean
    public NestApplication getNestApplication(ContainerProvider containerProvider) {
        return new NestApplication(containerProvider);
    }

    @Bean
    public EventBus getEventBus(BeanFinder beanFinder) {
        return new EventBus(beanFinder);
    }
}
