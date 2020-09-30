package com.zhaofujun.nest.context.loader;

import com.zhaofujun.nest.context.appservice.ServiceContext;
import com.zhaofujun.nest.context.appservice.ServiceContextManager;
import com.zhaofujun.nest.context.model.BaseEntity;
import com.zhaofujun.nest.standard.EntityLoader;
import com.zhaofujun.nest.standard.Identifier;

public class UnitOfWorkEntityLoader<T extends BaseEntity> implements EntityLoader<T> {


    private Class tClass;

    public UnitOfWorkEntityLoader(Class tClass) {
        this.tClass = tClass;
    }

    @Override
    public T create(Identifier id) {
        return create(tClass, id);
    }

    @Override
    public <U extends T> U create(Class uClass, Identifier id) {
        ServiceContext serviceContext = ServiceContextManager.get();
        if(serviceContext==null) return null;
        U entity = serviceContext
                .getEntity(uClass, id);
        return entity;
    }
}
