package com.zhaofujun.nest.event;

public interface ServiceContextListener extends NestEventListener {
    void serviceCreated(ServiceEvent serviceEvent);

    void serviceMethodStart(ServiceEvent serviceEvent, String methodName);

    void serviceMethodEnd(ServiceEvent serviceEvent, String methodName);

    void beforeCommit(ServiceEvent serviceEvent);

    void committed(ServiceEvent serviceEvent);

    void serviceEnd(ServiceEvent serviceEvent);
}
