package com.guoshouxiang.nest.event;

import com.guoshouxiang.nest.NestApplication;

import java.util.EventObject;

public class ApplicationEvent extends EventObject {
    private NestApplication application;

    public ApplicationEvent(Object source, NestApplication application) {
        super(source);
        this.application = application;
    }

    public NestApplication getApplication() {
        return application;
    }

}
