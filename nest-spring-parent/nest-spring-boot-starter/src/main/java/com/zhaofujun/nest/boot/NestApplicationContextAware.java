package com.zhaofujun.nest.boot;

import com.zhaofujun.nest.NestEngine;
import com.zhaofujun.nest.ddd.event.EventAppService;
import com.zhaofujun.nest.ddd.event.EventMessageRepository;
import com.zhaofujun.nest.provider.Container;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class NestApplicationContextAware implements ApplicationContextAware {
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        EventAppService eventAppService = applicationContext.getBean(EventAppService.class);

        EventMessageRepository eventMessageRepository = applicationContext.getBean(EventMessageRepository.class);

        Container container = applicationContext.getBean(Container.class);

        // 容器启动成功后， 初始化 NestEngine
        NestEngine nestEngine = new NestEngine();
        nestEngine.initEventEnvironment(eventAppService, eventMessageRepository);
        nestEngine.registerByContainer(container);
        nestEngine.start();
    }
}
