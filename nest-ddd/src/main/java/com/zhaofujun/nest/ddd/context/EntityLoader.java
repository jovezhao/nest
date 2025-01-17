package com.zhaofujun.nest.ddd.context;

import com.zhaofujun.nest.NestConst;
import com.zhaofujun.nest.ddd.Entity;
import com.zhaofujun.nest.ddd.Identifier;
import com.zhaofujun.nest.ddd.Repository;
import com.zhaofujun.nest.manager.CacheManager;
import com.zhaofujun.nest.manager.RepositoryManager;
import com.zhaofujun.nest.utils.EntityUtil;
import com.zhaofujun.nest.utils.cache.CacheClient;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class EntityLoader {

    public static <T extends Entity<I>, I extends Identifier> T load(Class<T> tClass, I identifier) {
        // 先通过上下文获取
        T entity = null;
        ServiceContext currentContext = ServiceContextManager.getCurrentContext();
        if (currentContext != null)
            entity = currentContext.load(tClass, identifier);
        if (entity != null)
            return entity;

        // 启动查询上下文
        QueryContext queryContext = QueryContextManager.newServiceContext();
        queryContext.begin();
        try {
            // 再通过缓存获取
            CacheClient cacheClient = CacheManager.getCacheClient(NestConst.entityCache);
            entity = cacheClient.get(tClass, EntityUtil.getKey(tClass, identifier));
            if (entity == null) {
                // 最后通过仓储获取
                Repository<T, I> repository = RepositoryManager.getRepository(tClass);
                entity = repository.getEntityById(tClass, identifier);
                if (entity != null)
                    // 获取成功后写入缓存
                    cacheClient.put(EntityUtil.getKey(entity), entity);
            }
            // 提交上下文
            queryContext.submit();
        } finally {
            // 清理上下文
            queryContext.end();
            QueryContextManager.pop();
        }
        return entity;
    }

    public static <T extends Entity<I>, I extends Identifier> Map<I, T> load(Class<T> tClass, Collection<I> identifiers) {
        Map<I, T> entityMap = new HashMap<>();
        // 先通过上下文获取
        Collection<I> ids = identifiers;
        if (!ids.isEmpty()) {
            ServiceContext currentContext = ServiceContextManager.getCurrentContext();
            if (currentContext != null) {
                Map<I, T> loadData = currentContext.load(tClass, ids);
                if (loadData != null) {
                    loadData.values().forEach(entity -> entityMap.put(entity.getId(), entity));
                    ids = ids.stream().filter(p -> !entityMap.containsKey(p)).collect(Collectors.toList()); //将没有查到的对象ID过滤出来进一步查询
                }
            }
        }

        // 启动查询上下文
        QueryContext queryContext = QueryContextManager.newServiceContext();
        queryContext.begin();
        try {

            //再通过缓存获取
            if (!ids.isEmpty()) {
                CacheClient cacheClient = CacheManager.getCacheClient(NestConst.entityCache);
                String[] keys = ids.stream().map(p -> EntityUtil.getKey(tClass, p)).toArray(String[]::new);
                Map<String, T> loadData = cacheClient.get(tClass, keys);
                loadData.values().forEach(entity -> entityMap.put(entity.getId(), entity));
                ids = ids.stream().filter(p -> !entityMap.containsKey(p)).collect(Collectors.toList()); //将没有相到的对象ID过滤出来进一步查询
            }

            //最后通过仓储获取
            if (!ids.isEmpty()) {
                CacheClient cacheClient = CacheManager.getCacheClient(NestConst.entityCache);
                Repository<T, I> repository = RepositoryManager.getRepository(tClass);
                Collection<T> loadData = repository.getEntityByIds(tClass, ids);
                loadData.forEach(entity -> {
                            //写回缓存
                            if (entity != null) {
                                entityMap.put(entity.getId(), entity);
                                cacheClient.put(EntityUtil.getKey(entity), entity);
                            }
                        }
                );

            }
            // 提交上下文
            queryContext.submit();
        } finally {
            // 清理上下文
            queryContext.end();
            QueryContextManager.pop();
        }

        return entityMap;
    }

}
