package com.zhaofujun.nest.context.loader;


import com.zhaofujun.nest.context.model.BaseEntity;
import com.zhaofujun.nest.context.model.Identifier;
import com.zhaofujun.nest.utils.EntityUtils;

/**
 * 用于仓储预加载时的实体构建器
 * 使用此方法加载的实体状态为加载中
 *
 * @param <T>
 */
class RepositoryPreEntityLoader<T extends BaseEntity> implements EntityLoader<T> {
    Class<T> tClass;

    public RepositoryPreEntityLoader(Class<T> tClass) {
        this.tClass = tClass;
    }

    @Override
    public T create(Identifier id) {
        return create(tClass, id);
    }

    @Override
    public <U extends T> U create(Class<U> uClass, Identifier id) {
        EntityLoader<U> entityLoader = new ConstructEntityLoader<>(uClass);
        U u = entityLoader.create(id);
        EntityUtils.load(u);
        return u;
    }
}
