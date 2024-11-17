package com.zhaofujun.nest.boot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.boot.context.logging.LoggingApplicationListener;
import org.springframework.context.ApplicationListener;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import com.zhaofujun.nest.NestEngine;
import com.zhaofujun.nest.ddd.event.EventAppService;
import com.zhaofujun.nest.provider.Container;

@Component
public class ApplicationStartdListener implements ApplicationListener<ApplicationStartedEvent>,Ordered {

    @Autowired
    private EventAppService eventAppService;
    @Autowired
    private Container container;

    @SuppressWarnings("null")
    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        
        // 容器启动成功后， 初始化 NestEngine
        NestEngine nestEngine = new NestEngine();
        nestEngine.setEventAppService(eventAppService);
        nestEngine.init(container);
        nestEngine.start();
    }

    @Override
    public int getOrder() {
        return LoggingApplicationListener.DEFAULT_ORDER+1;
    }
    

}
