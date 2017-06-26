package com.jovezhao.nest.ddd.builder;

import com.jovezhao.nest.ddd.BaseEntityObject;
import com.jovezhao.nest.ddd.Identifier;

/**
 * 用于仓储预加载时的实体构建器
 * 使用此方法加载的实体状态为加载中
 *
 * @param <T>
 */
class RepositoryPreloader<T extends BaseEntityObject> implements EntityLoader<T> {
    Class<T> tClass;

    public RepositoryPreloader(Class<T> tClass) {
        this.tClass = tClass;
    }

    @Override
    public T create(Identifier id) {
        T t = EntityObjectUtils.create(tClass);
        EntityObjectUtils.beginLoad(t);
        t.setId(id);
        return t;
    }

    @Override
    public <U extends T> U create(Class<U> uClass, Identifier id) {
        U u = EntityObjectUtils.create(uClass);
        EntityObjectUtils.beginLoad(u);
        u.setId(id);
        return u;
    }
}
