package com.zhaofujun.nest;

import com.zhaofujun.nest.configuration.ConfigurationItem;
import com.zhaofujun.nest.configuration.ConfigurationManager;
import com.zhaofujun.nest.configuration.MessageConfiguration;
import com.zhaofujun.nest.context.event.DefaultEventBus;
import com.zhaofujun.nest.context.event.resend.ResenderTimerTask;
import com.zhaofujun.nest.context.repository.RepositoryManager;
import com.zhaofujun.nest.standard.EventBus;
import com.zhaofujun.nest.standard.EventHandler;
import com.zhaofujun.nest.standard.Repository;
import com.zhaofujun.nest.context.appservice.ServiceContext;
import com.zhaofujun.nest.event.*;
import com.zhaofujun.nest.provider.Provider;
import com.zhaofujun.nest.provider.ProviderManage;

import java.sql.Time;
import java.util.Timer;


public class NestApplication {

    private MessageConfiguration messageConfiguration;
    private ConfigurationManager configurationManager;
    private EventListenerManager listenerManager;
    private RepositoryManager repositoryManager;
    private ProviderManage providerManage;
    private Timer timer = new Timer();


    public ConfigurationManager getConfigurationManager() {
        return this.configurationManager;
    }

    public ProviderManage getProviderManage() {
        return providerManage;
    }

    public EventListenerManager getListenerManager() {
        return listenerManager;
    }

    public RepositoryManager getRepositoryManager() {
        return repositoryManager;
    }

    NestApplication() {
        this.messageConfiguration = new MessageConfiguration();
        this.configurationManager = new ConfigurationManager();
        this.providerManage = new ProviderManage();
        this.listenerManager = new EventListenerManager();
        this.repositoryManager = new RepositoryManager();
    }

    public void setContainerProvider(ContainerProvider containerProvider) {
        this.configurationManager.addConfigurationItem(containerProvider.getInstances(ConfigurationItem.class));
        this.providerManage.addProvider(containerProvider.getInstances(Provider.class));
        this.listenerManager.addListeners(containerProvider.getInstances(NestEventListener.class));
        this.repositoryManager.addRepository(containerProvider.getInstances(Repository.class));
        EventBus eventBus = new DefaultEventBus();
        containerProvider.getInstances(EventHandler.class).forEach(eventHandler -> {
            eventBus.registerHandler(eventHandler);
        });
    }

    private static NestApplication application = new NestApplication();

    public static NestApplication current() {
        return application;
    }


    public MessageConfiguration getMessageConfiguration() {
        return messageConfiguration;
    }

    public void start() {
        onStarted();
        timer.schedule(new ResenderTimerTask(), 0, 1000);
    }

    public void close() {
        timer.cancel();
        onClosed();
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

    public void serviceMethodStart(ServiceContext serviceContext) {
        ServiceEvent serviceEvent = new ServiceEvent(this, serviceContext);
        this.listenerManager.publish(ServiceContextListener.class, p -> {
            p.serviceMethodStart(serviceEvent, serviceContext.getMethod());
        });
    }

    public void serviceMethodEnd(ServiceContext serviceContext) {
        ServiceEvent serviceEvent = new ServiceEvent(this, serviceContext);
        this.listenerManager.publish(ServiceContextListener.class, p -> {
            p.serviceMethodEnd(serviceEvent, serviceContext.getMethod());
        });
    }

    public void serviceEnd(ServiceContext serviceContext) {
        ServiceEvent serviceEvent = new ServiceEvent(this, serviceContext);
        this.listenerManager.publish(ServiceContextListener.class, p -> {
            p.serviceEnd(serviceEvent);
        });
    }
}


