package com.zhaofujun.nest;

import com.zhaofujun.nest.configuration.ConfigurationManager;
import com.zhaofujun.nest.core.BeanFinder;
import com.zhaofujun.nest.container.ContainerProvider;
import com.zhaofujun.nest.context.ServiceContext;
import com.zhaofujun.nest.event.*;


public class NestApplication {

    private EventListenerManager listenerManager;
    private ConfigurationManager configurationManager;
    private ContainerProvider containerProvider;

    public NestApplication(ContainerProvider containerProvider) {
        this.containerProvider = containerProvider;
        this.configurationManager =  ConfigurationManager.create(containerProvider);
        this.listenerManager = new EventListenerManager();

    }

    public ConfigurationManager getConfigurationManager() {
        return this.configurationManager;
    }


    public void start() {
        onStarted();
    }

    public void close() {
        onClosed();
    }

    public ServiceContext newInstance(Class serviceClass) {
        ServiceContext serviceContext = ServiceContext.newInstance(serviceClass, this);
        return serviceContext;
    }

    public BeanFinder getBeanFinder() {
        return this.containerProvider;
    }


    public void addApplicationListener(ApplicationListener applicationListener) {
        this.listenerManager.addListener(applicationListener);
    }

    public void addServiceContextListener(ServiceContextListener serviceContextListener) {
        this.listenerManager.addListener(serviceContextListener);
    }


    private void onStarted() {
        ApplicationEvent applicationEvent = new ApplicationEvent(this, this);
        this.listenerManager.publish(ApplicationListener.class, p -> {
            p.applicationStarted(applicationEvent);
        });
    }

    private void onClosed() {
        ApplicationEvent applicationEvent = new ApplicationEvent(this, this);
        this.listenerManager.publish(ApplicationListener.class, p -> {
            p.applicationClosed(applicationEvent);
        });
    }

    public void onServiceContextCreated(ServiceContext serviceContext) {
        ServiceEvent serviceEvent = new ServiceEvent(this, serviceContext);
        this.listenerManager.publish(ServiceContextListener.class, p -> {
            p.serviceCreated(serviceEvent);
        });
    }

    public void beforeCommit(ServiceContext serviceContext) {
        ServiceEvent serviceEvent = new ServiceEvent(this, serviceContext);
        this.listenerManager.publish(ServiceContextListener.class, p -> {
            p.beforeCommit(serviceEvent);
        });
    }

    public void committed(ServiceContext serviceContext) {
        ServiceEvent serviceEvent = new ServiceEvent(this, serviceContext);
        this.listenerManager.publish(ServiceContextListener.class, p -> {
            p.committed(serviceEvent);
        });
    }


}


