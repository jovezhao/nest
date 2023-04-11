package com.zhaofujun.nest3.application.loader;


import com.zhaofujun.nest3.application.CacheClient;
import com.zhaofujun.nest3.application.NestApplication;
import com.zhaofujun.nest3.model.*;
import com.zhaofujun.nest3.utils.EntityUtils;

public class RepositoryEntityLoader<T extends Entity> implements EntityLoader<T> {

    private Class<T> tClass;

    public RepositoryEntityLoader(Class<T> tClass) {
        this.tClass = tClass;

    }

    public T create(Identifier id) {
        return create(tClass, id);
    }

    @Override
    public <U extends T> U create(Class<U> uClass, Identifier identifier) {

        //先通过上下文获取
        EntityLoader<U> entityLoader = new UnitOfWorkEntityLoader<>(uClass);
        U result = entityLoader.create(identifier);


        //从缓存中获取实体
        CacheClient entityCache =EntityUtils.getEntityCacheClient();

        if (result == null) {
            entityLoader = new CacheEntityLoader<>(uClass, entityCache);
            result = entityLoader.create(identifier);
            if(result instanceof AggregateRoot) ((AggregateRoot<?>) result).attach();
        }
        //从仓储加载实体
        if (result == null) {
            Repository<U> repository = NestApplication.current().getRepository(uClass);
            result = repository.getEntityById(identifier);
            if(result instanceof AggregateRoot) ((AggregateRoot<?>) result).attach();
            //写回缓存
            entityCache.put(EntityUtils.getCacheKey(result), result);

        }
        return result;
    }


}

