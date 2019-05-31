package com.guoshouxiang.nest.event;

import com.guoshouxiang.nest.context.ServiceContext;

import java.util.EventObject;

public class ServiceEvent extends EventObject {
    private ServiceContext serviceContext;

    public ServiceEvent(Object source, ServiceContext serviceContext) {
        super(source);
        this.serviceContext = serviceContext;
    }
}
