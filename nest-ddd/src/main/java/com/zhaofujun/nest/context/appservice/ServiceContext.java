package com.zhaofujun.nest.context.appservice;


import com.zhaofujun.nest.context.event.delay.DelayMessageBacklog;
import com.zhaofujun.nest.context.event.message.MessageInfo;
import com.zhaofujun.nest.context.model.BaseEntity;
import com.zhaofujun.nest.standard.Identifier;

public class ServiceContext {

    private Class serviceClass;
    private Object applicationService;
    private String method;
    private UnitOfWork unitOfWork;

    public ServiceContext(Class serviceClass, Object applicationService, String method,TransactionManager transactionManager ) {
        this.serviceClass = serviceClass;
        this.applicationService = applicationService;
        this.method = method;
        this.unitOfWork = new UnitOfWork(this,transactionManager);
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

    public void commit() {
        unitOfWork.commit();
    }

    public void put(BaseEntity entity) {
        this.unitOfWork.put(entity);
    }

    public <T extends BaseEntity> T getEntity(Class tClass, Identifier identifier) {
        return this.unitOfWork.getEntity(tClass, identifier);
    }

    public void addMessageBacklog(String messageGroup, MessageInfo messageInfo) {
        this.unitOfWork.addMessageBacklog(messageGroup, messageInfo);
    }

    public void addDelayMessageBacklog(DelayMessageBacklog delayMessageBacklog) {
        this.unitOfWork.addDelayMessageBacklog(delayMessageBacklog);
    }
}

