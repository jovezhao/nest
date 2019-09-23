package com.zhaofujun.nest;

import com.zhaofujun.nest.configuration.ConfigurationManager;
import com.zhaofujun.nest.container.BeanFinder;
import com.zhaofujun.nest.container.ContainerProvider;
import com.zhaofujun.nest.context.ServiceContext;
import com.guoshouxiang.nest.event.*;
import com.zhaofujun.nest.event.*;


public class NestApplication {

    private EventListenerManager listenerManager;
    private ConfigurationManager configurationManager;
    private ContainerProvider containerProvider;

    public NestApplication(ContainerProvider containerProvider) {
        this.containerProvider = containerProvider;
        this.configurationManager = new ConfigurationManager(containerProvider);
        this.listenerManager = new EventListenerManager();
    }

    public ConfigurationManager getConfigurationManager() {
        return this.configurationManager;
    }


    public void start() {

        containerProvider.initialize();

        onStarted();
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


    public void onStarted() {
        ApplicationEvent applicationEvent = new ApplicationEvent(this, this);
        this.listenerManager.publish(ApplicationListener.class, p -> {
            p.applicationStarted(applicationEvent);
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


