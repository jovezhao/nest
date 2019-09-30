package com.zhaofujun.nest.context;

import com.zhaofujun.nest.NestApplication;
import com.zhaofujun.nest.container.BeanFinder;

public class ServiceContext {

    private Class serviceClass;
    private ContextUnitOfWork contextUnitOfWork;
    private BeanFinder beanFinder;
    private NestApplication application;


    public ServiceContext(Class serviceClass, BeanFinder beanFinder) {
        this.beanFinder = beanFinder;
        this.serviceClass = serviceClass;
        this.contextUnitOfWork = new ContextUnitOfWork();
        this.application = beanFinder.getInstance(NestApplication.class);
    }


    public Class getServiceClass() {
        return serviceClass;
    }

    public static ServiceContext getCurrent() {
        return ServiceContextManager.get();
    }

    public static ServiceContext newInstance(Class serviceClass, BeanFinder beanFinder) {
        ServiceContext serviceContext = new ServiceContext(serviceClass, beanFinder);
        ServiceContextManager.set(serviceContext);

        serviceContext.getApplication().onServiceContextCreated(serviceContext);
        return serviceContext;
    }

    public ContextUnitOfWork getContextUnitOfWork() {
        return contextUnitOfWork;
    }


    public BeanFinder getBeanFinder() {
        return beanFinder;
    }

    public NestApplication getApplication() {
        return application;
    }
}

