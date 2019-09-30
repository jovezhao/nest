package com.zhaofujun.nest.event;

import java.lang.reflect.Method;
import java.util.EventListener;

public interface ServiceContextListener extends EventListener {
    void serviceCreated(ServiceEvent serviceEvent);

    void serviceMethodStart(ServiceEvent serviceEvent, Method method);

    void serviceMethodEnd(ServiceEvent serviceEvent, Method method);

    void beforeCommit(ServiceEvent serviceEvent);
    void committed(ServiceEvent serviceEvent);

    void serviceEnd(ServiceEvent serviceEvent);
}
