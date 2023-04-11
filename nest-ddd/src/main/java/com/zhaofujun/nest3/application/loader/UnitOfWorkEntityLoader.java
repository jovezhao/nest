package com.zhaofujun.nest3.application.loader;


import com.zhaofujun.nest3.application.NestApplication;
import com.zhaofujun.nest3.application.context.ServiceContext;
import com.zhaofujun.nest3.model.Entity;
import com.zhaofujun.nest3.model.EntityLoader;
import com.zhaofujun.nest3.model.Identifier;

public class UnitOfWorkEntityLoader<T extends Entity> implements EntityLoader<T> {


    private Class<T> tClass;

    public UnitOfWorkEntityLoader(Class<T> tClass) {
        this.tClass = tClass;
    }

    @Override
    public T create(Identifier id) {
        return create(tClass, id);
    }

    @Override
    public <U extends T> U create(Class<U> uClass, Identifier id) {
        ServiceContext serviceContext = NestApplication.currentServiceContext();
        if (serviceContext == null) return null;
        U entity = serviceContext
                .getEntity(uClass, id);
        return entity;
    }
}
