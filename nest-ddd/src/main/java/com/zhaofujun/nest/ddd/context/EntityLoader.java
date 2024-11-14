package com.zhaofujun.nest.ddd.context;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.zhaofujun.nest.NestConst;
import com.zhaofujun.nest.ddd.Entity;
import com.zhaofujun.nest.ddd.Identifier;
import com.zhaofujun.nest.manager.CacheManager;
import com.zhaofujun.nest.manager.RepositoryManager;
import com.zhaofujun.nest.utils.EntityUtil;
import com.zhaofujun.nest.utils.cache.CacheClient;

public class EntityLoader {

    private static TransmittableThreadLocal<List<String>> threadLocal = new TransmittableThreadLocal<>();

    private static List<String> getThreadLocalValue() {
        List<String> keyList = threadLocal.get();
        if (keyList == null) {
            keyList = new ArrayList<>();
            threadLocal.set(keyList);
        }
        return keyList;
    }

    public static <T extends Entity> T load(Class<T> tClass, Identifier identifier) {
        // 先通过上下文获取
        T entity = null;
        ServiceContext currentContext = ServiceContextManager.getCurrentContext();
        if (currentContext != null)
            entity = currentContext.load(tClass, identifier);
        if (entity != null)
            return entity;

        // 配置上下文为加载中状态
        getThreadLocalValue().add(EntityUtil.getKey(tClass, identifier));
        // 再通过缓存获取
        CacheClient cacheClient = CacheManager.getCacheClient(NestConst.entityCache);
        entity = cacheClient.get(tClass, EntityUtil.getKey(tClass, identifier));
        if (entity == null) {
            // 最后通过仓储获取
            entity = (T) RepositoryManager.getRepository(tClass).getEntityById(tClass, identifier);
            if (entity != null)
                // 获取成功后写入缓存
                cacheClient.put(EntityUtil.getKey(entity), entity);
        }
        // 清除加载中状态
        getThreadLocalValue().remove(EntityUtil.getKey(tClass, identifier));
        // 完成准备
        if (entity != null)
            entity._ready();
        return entity;
    }

    public static <T extends Entity> boolean isLoading(Class<T> tClass, Identifier identifier) {
        return getThreadLocalValue().contains(EntityUtil.getKey(tClass, identifier));
    }
}
