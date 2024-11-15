package com.zhaofujun.nest.boot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.stereotype.Component;

import com.zhaofujun.nest.NestEngine;
import com.zhaofujun.nest.ddd.event.EventAppService;
import com.zhaofujun.nest.provider.Container;

@Component
public class ApplicationStartdListener implements ApplicationListener<ContextStartedEvent> {

    @Autowired
    private EventAppService eventAppService;
    @Autowired
    private Container container;

    @SuppressWarnings("null")
    @Override
    public void onApplicationEvent(ContextStartedEvent event) {
        
        // 容器启动成功后， 初始化 NestEngine
        NestEngine nestEngine = new NestEngine();
        nestEngine.setEventAppService(eventAppService);
        nestEngine.init(container);
        nestEngine.start();
    }
    

}
