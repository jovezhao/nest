package com.zhaofujun.nest.context.loader;

import com.zhaofujun.nest.context.ServiceContextManager;
import com.zhaofujun.nest.core.*;
import com.zhaofujun.nest.cache.CacheClientFactory;
import com.zhaofujun.nest.context.ServiceContext;
import com.zhaofujun.nest.context.model.Entity;
import com.zhaofujun.nest.utils.EntityCacheUtils;
import com.zhaofujun.nest.utils.EntityUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class CacheEntityLoader<T extends Entity> implements EntityLoader<T> {
    private Class tClass;
    private CacheClient cacheClient;


    public CacheEntityLoader(Class tClass) {

        BeanFinder beanFinder = ServiceContextManager.getCurrent().getApplication().getBeanFinder();
        this.tClass = tClass;
        CacheClientFactory cacheClientFactory = new CacheClientFactory(beanFinder);
        this.cacheClient = cacheClientFactory.getCacheClient(EntityCacheUtils.getCacheCode());
    }

    @Override
    public T create(Identifier id) {
        return create(tClass, id);
    }

    @Override
    public <U extends T> U create(Class uClass, Identifier id) {

        String cacheKey = EntityCacheUtils.getCacheKey(uClass, id);
        U u = cacheClient.get(uClass, cacheKey);

        if (u == null) return null;

        Entity entity = toEntityObject(u);


        return (U) entity;
    }


    private Entity toEntityObject(Entity entityObject) {

        if (entityObject == null) return null;

        if (EntityUtils.isRepresented(entityObject)) return entityObject;


        Entity result = EntityCreate.create(entityObject.getClass(), entityObject.getId(), false, true);

        List<Field> fieldList = new ArrayList<>();
        Class aClass = entityObject.getClass();
        while (aClass != null) {

            Field[] fields = aClass.getDeclaredFields();
            fieldList.addAll(Arrays.asList(fields));
            aClass = aClass.getSuperclass();
        }


        fieldList.forEach(p -> {
            try {
                if (Modifier.isStatic(p.getModifiers()) || Modifier.isFinal(p.getModifiers()))
                    return;

                if (p.getName().startsWith("_"))
                    return;

                p.setAccessible(true);
                if (Entity.class.isAssignableFrom(p.getType())) {
                    Entity v = (Entity) p.get(entityObject);
                    if (v != null) {
                        Entity entity = new RepositoryEntityLoader(p.getType()).create(v.getId());
                        p.set(result, entity);
                    }
//                    p.set(result, toEntityObject(v));


                } else {
                    p.set(result, p.get(entityObject));
                }


            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });
        EntityUtils.setLoading(result, false);

        return result;
    }

}
