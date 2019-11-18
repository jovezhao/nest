package com.zhaofujun.nest.context.loader;

import com.zhaofujun.nest.core.CacheClient;
import com.zhaofujun.nest.cache.CacheClientFactory;
import com.zhaofujun.nest.core.BeanFinder;
import com.zhaofujun.nest.context.ServiceContext;
import com.zhaofujun.nest.context.model.Entity;
import com.zhaofujun.nest.core.Identifier;
import com.zhaofujun.nest.core.EntityLoader;
import com.zhaofujun.nest.utils.EntityCacheUtils;
import com.zhaofujun.nest.utils.EntityUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class CacheEntityLoader<T extends Entity> implements EntityLoader<T> {
    private Class<T> tClass;
    private CacheClient cacheClient;


    public CacheEntityLoader(Class<T> tClass) {

        BeanFinder beanFinder = ServiceContext.getCurrent().getApplication().getBeanFinder();
        this.tClass = tClass;
        CacheClientFactory cacheClientFactory = new CacheClientFactory(beanFinder);
        this.cacheClient = cacheClientFactory.getCacheClient(EntityCacheUtils.getCacheCode());
    }

    @Override
    public T create(Identifier id) {
        return create(tClass, id);
    }

    @Override
    public <U extends T> U create(Class<U> uClass, Identifier id) {

        String cacheKey = EntityCacheUtils.getCacheKey(uClass, id);
        U u = cacheClient.get(uClass, cacheKey);

        Entity entity = toEntityObject(u);
        return (U) entity;
    }


    private Entity toEntityObject(Entity entityObject) {
        if (entityObject == null) return null;

        if (EntityUtils.isRepresented(entityObject)) return entityObject;


        Entity result = EntityUtils.create(entityObject.getClass(), entityObject.getId(), false, true);

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
                    p.set(result, toEntityObject(v));


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
