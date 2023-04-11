package com.zhaofujun.nest3.application.manager;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.zhaofujun.nest3.application.NestApplication;
import com.zhaofujun.nest3.application.context.ServiceContext;
import com.zhaofujun.nest3.application.context.ServiceContextBox;
import com.zhaofujun.nest3.application.context.TransactionManager;

public class ServiceContextManager {
    private TransmittableThreadLocal<ServiceContextBox> threadLocal=new TransmittableThreadLocal<>();
    private NestApplication nestApplication;

    public ServiceContextManager(NestApplication nestApplication) {
        this.nestApplication = nestApplication;
    }

    public ServiceContext newServiceContext(Class serviceClass, Object applicationService, String method, TransactionManager transactionManager){
        ServiceContext serviceContext = new ServiceContext(this.nestApplication,serviceClass, applicationService, method, transactionManager);

        ServiceContextBox serviceContextBox = threadLocal.get();
        if(serviceContextBox==null){
            serviceContextBox=new ServiceContextBox(serviceContext,null);
            threadLocal.set(serviceContextBox);
        }else {
            if(serviceContextBox.owner()){
                serviceContextBox.push(serviceContext);
            }else {
                ServiceContextBox newBox = new ServiceContextBox(serviceContext, serviceContextBox);
                threadLocal.set(newBox);
            }
        }
        return serviceContext;
    }

    public ServiceContext getCurrentContext(){
        ServiceContextBox serviceContextBox = threadLocal.get();
        if(serviceContextBox==null) return null;
        return serviceContextBox.get();
    }
    public void remove(){
        ServiceContextBox serviceContextBox = threadLocal.get();
        if(serviceContextBox==null) return;
        if(serviceContextBox.owner()){
            if (serviceContextBox.onlyOne()) {
                threadLocal.set(serviceContextBox.getParent());
            }else{
                serviceContextBox.pop();
            }
        }
    }
}
