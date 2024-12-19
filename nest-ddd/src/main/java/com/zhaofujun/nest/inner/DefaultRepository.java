package com.zhaofujun.nest.inner;

import java.lang.reflect.Type;

import com.zhaofujun.nest.NestConst;
import com.zhaofujun.nest.ddd.Entity;
import com.zhaofujun.nest.ddd.Identifier;
import com.zhaofujun.nest.ddd.Repository;
import com.zhaofujun.nest.manager.CacheManager;
import com.zhaofujun.nest.utils.EntityUtil;
import com.zhaofujun.nest.utils.JsonUtil;
import com.zhaofujun.nest.utils.StringUtil;
import com.zhaofujun.nest.utils.cache.CacheClient;

public class DefaultRepository implements Repository<Entity<Identifier>, Identifier> {
    private CacheClient cacheClient = CacheManager.getCacheClient(NestConst.defaultRepositoryCache);

    @Override
    public Class getEntityClass() {
        return Entity.class;
    }

    @Override
    public Entity<Identifier> getEntityById(Class<Entity<Identifier>> tClass, Identifier identifier) {
        String entityString = cacheClient.get(EntityUtil.getKey(tClass, identifier));
        System.out.println("get" + tClass.getName() + ":" + entityString);
        if (StringUtil.isEmpty(entityString))
            return null;
        return JsonUtil.parseObject(entityString, tClass);
    }

    @Override
    public void insert(Entity<Identifier> entity) {
        String entityString = JsonUtil.toJsonString(entity);
        System.out.println("insert "+ entity.getClass().getName() + ":"  + entityString);
        cacheClient.put(EntityUtil.getKey(entity), entityString);
    }

    @Override
    public void update(Entity<Identifier> entity) {
        String entityString = JsonUtil.toJsonString(entity);
        System.out.println("update "+ entity.getClass().getName() + ":"  + entityString);

        cacheClient.put(EntityUtil.getKey(entity), entityString);
    }

    @Override
    public void delete(Entity<Identifier> entity) {
        cacheClient.remove(EntityUtil.getKey(entity));
    }

}