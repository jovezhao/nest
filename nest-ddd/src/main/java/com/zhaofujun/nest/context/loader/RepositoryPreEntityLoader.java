package com.zhaofujun.nest.context.loader;


import com.zhaofujun.nest.context.ServiceContext;
import com.zhaofujun.nest.context.ServiceContextManager;
import com.zhaofujun.nest.context.model.Entity;
import com.zhaofujun.nest.core.Identifier;
import com.zhaofujun.nest.core.EntityLoader;
import com.zhaofujun.nest.utils.EntityUtils;

/**
 * 用于仓储预加载时的实体构建器
 * 使用此方法加载的实体状态为加载中
 *
 * @param <T>
 */
class RepositoryPreEntityLoader<T extends Entity> implements EntityLoader<T> {
    private Class tClass;

    public RepositoryPreEntityLoader(Class tClass) {
        this.tClass = tClass;
    }

    @Override
    public T create(Identifier id) {
        return create(tClass, id);
    }

    @Override
    public <U extends T> U create(Class uClass, Identifier id) {
        U u = EntityCreate.create(uClass, id, false, true);

        return u;
    }
}
