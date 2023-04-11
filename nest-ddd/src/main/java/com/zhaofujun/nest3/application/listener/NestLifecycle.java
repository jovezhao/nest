package com.zhaofujun.nest3.application.listener;

public enum NestLifecycle {
    BeforeStart(ApplicationListener.ApplicationBeforeStartListener.class),
    Started(ApplicationListener.ApplicationStartedListener.class),
    BeforeClose(ApplicationListener.ApplicationBeforeCloseListener.class),
    Closed(ApplicationListener.ApplicationClosedListener.class);


    private Class listenerClass;

    NestLifecycle(Class listenerClass) {
        this.listenerClass = listenerClass;
    }

    public Class getListenerClass() {
        return listenerClass;
    }
}
