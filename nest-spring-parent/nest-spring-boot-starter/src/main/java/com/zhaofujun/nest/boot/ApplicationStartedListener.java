package com.zhaofujun.nest.boot;

import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.boot.context.logging.LoggingApplicationListener;
import org.springframework.context.ApplicationListener;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import com.zhaofujun.nest.NestEngine;
import com.zhaofujun.nest.ddd.event.EventAppService;
import com.zhaofujun.nest.ddd.event.EventMessageRepository;
import com.zhaofujun.nest.provider.Container;

@Component
public class ApplicationStartedListener implements ApplicationListener<ApplicationStartedEvent>, Ordered {

    


    


    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {


    }

    @Override
    public int getOrder() {
        return LoggingApplicationListener.DEFAULT_ORDER + 1;
    }

}
