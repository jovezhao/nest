package com.zhaofujun.nest.event;

import java.lang.reflect.Method;
import java.util.EventListener;

public interface ServiceContextListener extends NestEventListener {
    void serviceCreated(ServiceEvent serviceEvent);

    void serviceMethodStart(ServiceEvent serviceEvent, String methodName);

    void serviceMethodEnd(ServiceEvent serviceEvent, String methodName);

    void beforeCommit(ServiceEvent serviceEvent);
    void committed(ServiceEvent serviceEvent);

    void serviceEnd(ServiceEvent serviceEvent);
}
