package com.zhaofujun.nest.context.loader;


import com.zhaofujun.nest.context.ServiceContextManager;
import com.zhaofujun.nest.context.model.Entity;
import com.zhaofujun.nest.core.*;
import com.zhaofujun.nest.cache.CacheClientFactory;
import com.zhaofujun.nest.context.ServiceContext;
import com.zhaofujun.nest.context.model.Role;
import com.zhaofujun.nest.context.repository.RepositoryFactory;
import com.zhaofujun.nest.utils.EntityCacheUtils;
import com.zhaofujun.nest.utils.EntityUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;


public class RepositoryEntityLoader<T extends Entity> implements EntityLoader<T> {

    private Class tClass;
    private CacheClient cacheClient;

    public RepositoryEntityLoader(Class tClass) {
        this.tClass = tClass;
        BeanFinder beanFinder = ServiceContextManager.getCurrent().getApplication().getBeanFinder();
        CacheClientFactory cacheClientFactory = new CacheClientFactory(beanFinder);
        cacheClient = cacheClientFactory.getCacheClient(EntityCacheUtils.getCacheCode());
    }

    public T create(Identifier id) {
        return create(tClass, id);
    }

    private void setActor(Identifier id, T t, Class tClass) {
        if (Role.class.isInstance(t)) {
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
            Entity entity = repositoryEntityLoader.create(actorId);
            EntityUtils.setValue(Role.class, t, "actor", entity);
        }
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
        if (result != null) return result;


        Repository<U> repository = RepositoryFactory.create(uClass);


        result = repository.getEntityById(id, new RepositoryPreEntityLoader<U>(uClass));
        if (result != null) {
            EntityUtils.setLoading(result, false);


            cacheClient.put(EntityCacheUtils.getCacheKey(result), result);
        }


        setActor(id, result, uClass);

        return result;
    }


}

