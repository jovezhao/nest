package com.zhaofujun.nest.context.loader;

import com.zhaofujun.nest.context.ServiceContext;
import com.zhaofujun.nest.context.ServiceContextManager;
import com.zhaofujun.nest.context.model.Entity;
import com.zhaofujun.nest.core.Identifier;
import com.zhaofujun.nest.core.EntityLoader;

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
        U entity = ServiceContextManager.getCurrent()
                .getContextUnitOfWork()
                .getEntity(uClass, id);
        return entity;
    }
}
