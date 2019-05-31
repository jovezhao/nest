package com.guoshouxiang.nest.context.loader;


import com.guoshouxiang.nest.cache.CacheClient;
import com.guoshouxiang.nest.cache.CacheClientFactory;
import com.guoshouxiang.nest.container.BeanFinder;
import com.guoshouxiang.nest.context.ServiceContext;
import com.guoshouxiang.nest.context.model.BaseEntity;
import com.guoshouxiang.nest.context.model.BaseRole;
import com.guoshouxiang.nest.context.model.Identifier;
import com.guoshouxiang.nest.context.repository.Repository;
import com.guoshouxiang.nest.context.repository.RepositoryFactory;
import com.guoshouxiang.nest.context.repository.RoleRepository;
import com.guoshouxiang.nest.utils.EntityCacheUtils;
import com.guoshouxiang.nest.utils.EntityUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;


public class RepositoryEntityLoader<T extends BaseEntity> implements EntityLoader<T> {

    private Class<T> tClass;
    private CacheClient cacheClient;

    public RepositoryEntityLoader(Class<T> tClass) {
        this.tClass = tClass;
        BeanFinder beanFinder = ServiceContext.getCurrent().getBeanFinder();
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

