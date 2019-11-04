package com.zhaofujun.nest.event;

import java.util.EventListener;

public interface ApplicationListener extends EventListener {

    void applicationStarted(ApplicationEvent applicationEvent);

    void applicationClosed(ApplicationEvent applicationEvent);
}
