package com.zhaofujun.nest.event;

import java.util.EventListener;

public interface ApplicationListener extends NestEventListener {

    void applicationStarted(ApplicationEvent applicationEvent);

    void applicationClosed(ApplicationEvent applicationEvent);
}

