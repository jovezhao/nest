package com.zhaofujun.nest.context.loader;


import com.zhaofujun.nest.core.CacheClient;
import com.zhaofujun.nest.cache.CacheClientFactory;
import com.zhaofujun.nest.core.BeanFinder;
import com.zhaofujun.nest.context.ServiceContext;
import com.zhaofujun.nest.core.BaseEntity;
import com.zhaofujun.nest.core.BaseRole;
import com.zhaofujun.nest.core.Identifier;
import com.zhaofujun.nest.core.Repository;
import com.zhaofujun.nest.context.repository.RepositoryFactory;
import com.zhaofujun.nest.core.RoleRepository;
import com.zhaofujun.nest.core.EntityLoader;
import com.zhaofujun.nest.utils.EntityCacheUtils;
import com.zhaofujun.nest.utils.EntityUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;


public class RepositoryEntityLoader<T extends BaseEntity> implements EntityLoader<T> {

    private Class<T> tClass;
    private CacheClient cacheClient;

    public RepositoryEntityLoader(Class<T> tClass) {
        this.tClass = tClass;
        BeanFinder beanFinder = ServiceContext.getCurrent().getApplication().getBeanFinder();
        CacheClientFactory cacheClientFactory = new CacheClientFactory(beanFinder);
        cacheClient = cacheClientFactory.getCacheClient(EntityCacheUtils.getCacheCode());
    }

    public T create(Identifier id) {
        return create(tClass, id);
    }

    private void setActor(Identifier id, T t, Class tClass) {
        if (BaseRole.class.isInstance(t)) {
            RoleRepository roleRepository = (RoleRepository) RepositoryFactory.create(tClass);
            Identifier actorId = roleRepository.getActorIdByRoleId(id);
            ParameterizedType parameterizedType = (ParameterizedType) tClass.getGenericSuperclass();
            Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
            String typeName = actualTypeArguments[0].getTypeName();
            Class actorClass = null;
            try {
                actorClass = Class.forName(typeName);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            RepositoryEntityLoader repositoryEntityLoader = new RepositoryEntityLoader(actorClass);
            BaseEntity baseEntity = repositoryEntityLoader.create(actorId);
            EntityUtils.setValue(BaseRole.class, t, "actor", baseEntity);
        }
    }

    @Override
    public <U extends T> U create(Class<U> uClass, Identifier id) {

        EntityLoader<U> entityLoader = null;
        U result = null;
        //从上下文中获取实体
        entityLoader = new UnitOfWorkEntityLoader<>(uClass);
        result = entityLoader.create(id);
        if (result != null) return result;


        //从缓存中获取实体
        entityLoader = new CacheEntityLoader<>(uClass);
        result = entityLoader.create(id);
        if (result != null) return result;

        Repository<U> repository = RepositoryFactory.create(uClass);
        result = repository.getEntityById(id, new RepositoryPreEntityLoader<U>(uClass));
        if (result != null) {
            EntityUtils.endLoad(result);

            cacheClient.put(EntityCacheUtils.getCacheKey(result), result);
        }


        setActor(id, result, uClass);

        return result;
    }


}

