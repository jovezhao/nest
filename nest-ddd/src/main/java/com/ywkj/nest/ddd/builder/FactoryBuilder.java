package com.ywkj.nest.ddd.builder;

import com.ywkj.nest.ddd.EntityObject;

public class FactoryBuilder<T extends EntityObject> implements IBuilder<T> {
    Class<T> tClass;

    public FactoryBuilder(Class<T> tClass) {
        this.tClass = tClass;
    }

    @Override
    public T build(String id) {
        T t = EntityObjectFactory.create(tClass);
        t.setId(id);
        return t;
    }
}
