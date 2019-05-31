package com.guoshouxiang.nest.event;

import java.util.EventListener;

public interface ApplicationListener extends EventListener {

    void applicationStarted(ApplicationEvent applicationEvent);
}
