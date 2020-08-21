package com.zhaofujun.nest.context.loader;


import com.zhaofujun.nest.NestApplication;
import com.zhaofujun.nest.cache.CacheClient;
import com.zhaofujun.nest.context.model.BaseEntity;
import com.zhaofujun.nest.standard.Repository;
import com.zhaofujun.nest.cache.CacheClientFactory;
import com.zhaofujun.nest.standard.EntityLoader;
import com.zhaofujun.nest.standard.Identifier;
import com.zhaofujun.nest.utils.EntityCacheUtils;


public class RepositoryEntityLoader<T extends BaseEntity> implements EntityLoader<T> {

    private Class tClass;
    private CacheClient cacheClient;

    public RepositoryEntityLoader(Class tClass) {
        this.tClass = tClass;

        cacheClient = CacheClientFactory.getCacheClient(EntityCacheUtils.getCacheCode());
    }

    public T create(Identifier id) {
        return create(tClass, id);
    }

    @Override
    public <U extends T> U create(Class uClass, Identifier id) {

        EntityLoader<U> entityLoader = null;
        U result = null;
        //从当前上下文中获取实体
        entityLoader = new UnitOfWorkEntityLoader<>(uClass);
        result = entityLoader.create(id);
        if (result != null) return result;


        //从缓存中获取实体
        entityLoader = new CacheEntityLoader<>(uClass);
        result = entityLoader.create(id);
        if (result != null){
            result.ready();
            return result;
        }


        Repository<U> repository = NestApplication.current().getRepositoryManager().create(uClass);

        result = repository.getEntityById(id, new RepositoryPreEntityLoader<U>(uClass));
        if (result != null) {
            result.ready();
            cacheClient.put(EntityCacheUtils.getCacheKey(result), result);
        }

        return result;
    }


}

