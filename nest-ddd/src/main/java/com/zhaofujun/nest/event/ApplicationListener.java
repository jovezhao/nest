package com.zhaofujun.nest.event;


public interface ApplicationListener extends NestEventListener {

    void applicationStarted(ApplicationEvent applicationEvent);

    void applicationClosed(ApplicationEvent applicationEvent);
}

