package com.guoshouxiang.nest.context.loader;

import com.guoshouxiang.nest.context.ServiceContext;
import com.guoshouxiang.nest.context.model.BaseEntity;
import com.guoshouxiang.nest.context.model.Identifier;

public class UnitOfWorkEntityLoader<T extends BaseEntity> implements EntityLoader<T> {


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
        U entity = ServiceContext.getCurrent()
                .getContextUnitOfWork()
                .getEntity(uClass, id);
        return entity;
    }
}
