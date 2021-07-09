package com.zhaofujun.nest.event;

import com.zhaofujun.nest.context.appservice.ServiceContext;

import java.util.EventObject;

public class ServiceEvent extends EventObject {
    private ServiceContext serviceContext;


    public ServiceContext getServiceContext() {
        return serviceContext;
    }

    public ServiceEvent(Object source, ServiceContext serviceContext) {
        super(source);
        this.serviceContext = serviceContext;
    }
}
