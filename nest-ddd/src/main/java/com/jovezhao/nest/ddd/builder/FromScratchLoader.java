package com.jovezhao.nest.ddd.builder;

import com.jovezhao.nest.ddd.BaseEntityObject;
import com.jovezhao.nest.ddd.Identifier;

/**
 * 无中生有类实体的构建
 * @param <T>
 */
public class FromScratchLoader<T extends BaseEntityObject> implements EntityLoader<T> {
    private Class<T> tClass;

    public FromScratchLoader(Class<T> tClass) {
        this.tClass = tClass;
    }

    @Override
    public T create(Identifier id) {
        T t = EntityObjectUtils.create(tClass);
        t.setId(id);
        return t;
    }

    @Override
    public <U extends T> U create(Class<U> uClass, Identifier id) {
        U u = EntityObjectUtils.create(uClass);
        u.setId(id);
        return u;
    }
}
