package com.zhaofujun.nest.context.appservice;


import com.zhaofujun.nest.context.event.message.MessageInfo;
import com.zhaofujun.nest.context.model.BaseEntity;
import com.zhaofujun.nest.standard.Identifier;

public class ServiceContext {

    private Class serviceClass;
    private Object applicationService;
    private String method;
    private UnitOfWork unitOfWork;
    private UnitOfWorkCommitor commitor;

    public ServiceContext(Class serviceClass, Object applicationService, String method, UnitOfWorkCommitor commitor) {
        this.serviceClass = serviceClass;
        this.applicationService = applicationService;
        this.method = method;
        this.commitor = commitor;
        this.unitOfWork = new UnitOfWork();
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
        this.commitor.setUnitOfWork(unitOfWork);
        this.commitor.commit();
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
}

