package com.zhaofujun.nest.event;

public interface ServiceContextListener extends NestEventListener {
    default void serviceCreated(ServiceEvent serviceEvent) {
    }

    default void serviceMethodStart(ServiceEvent serviceEvent, String methodName) {
    }

    default void serviceMethodEnd(ServiceEvent serviceEvent, String methodName) {
    }

    default void beforeCommit(ServiceEvent serviceEvent) {
    }

    default void committed(ServiceEvent serviceEvent) {
    }

    default void serviceEnd(ServiceEvent serviceEvent) {
    }
}
