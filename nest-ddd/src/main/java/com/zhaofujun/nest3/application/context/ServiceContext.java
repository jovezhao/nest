package com.zhaofujun.nest3.application.context;


import com.zhaofujun.nest3.application.NestApplication;
import com.zhaofujun.nest3.application.event.EventInfo;
import com.zhaofujun.nest3.model.AggregateRoot;
import com.zhaofujun.nest3.model.Entity;
import com.zhaofujun.nest3.model.Identifier;

import java.util.ArrayList;
import java.util.List;

public class ServiceContext {

    private Class serviceClass;
    private Object applicationService;
    private String method;

    private NestApplication nestApplication;

    private List<AggregateRoot> aggregateRoots = new ArrayList<>();
    private List<EventInfo> eventInfos = new ArrayList<>();


    private EntityDispatcher entityDispatcher;
    private MessageDispatcher messageDispatcher;


//    private MessageUnitOfWork messageUnitOfWork;

    public ServiceContext(NestApplication nestApplication, Class serviceClass, Object applicationService, String method, TransactionManager transactionManager) {
        this.nestApplication = nestApplication;
        this.serviceClass = serviceClass;
        this.applicationService = applicationService;
        this.method = method;
        this.entityDispatcher = new EntityDispatcherImpl(this, transactionManager);
        //注册回调方法，用来协调与messageDispatcher进行通信
        this.entityDispatcher.setCommittedCallback(p->{
            //完成本地仓库提交后，发起事件提交
            messageDispatcher.commit();
        });

    }

    public void addAggregateRoot(AggregateRoot aggregateRoot) {
        this.aggregateRoots.add(aggregateRoot);
        entityDispatcher.initRoot(aggregateRoot);
    }


    public Class getServiceClass() {
        return serviceClass;
    }

    public Object getApplicationService() {
        return applicationService;
    }

    public String getMethod() {
        return method;
    }

    public NestApplication getNestApplication() {
        return nestApplication;
    }

    public <U extends Entity> U getEntity(Class<U> uClass, Identifier id) {
        return entityDispatcher.getEntity(uClass, id);
    }

    public void commit() {

        aggregateRoots.stream()
                .filter(p -> !p.is__deleted()) //过滤掉需要删除的项
                .forEach(p -> {
                    entityDispatcher.endRoot(p);
                });

        entityDispatcher.dispatch();
    }


    public void addEvent(EventInfo eventInfo) {
        eventInfos.add(eventInfo);
    }
}

