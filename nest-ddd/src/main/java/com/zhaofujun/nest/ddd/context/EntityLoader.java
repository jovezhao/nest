package com.zhaofujun.nest.ddd.context;

import com.zhaofujun.nest.NestConst;
import com.zhaofujun.nest.ddd.Entity;
import com.zhaofujun.nest.ddd.Identifier;
import com.zhaofujun.nest.ddd.Repository;
import com.zhaofujun.nest.manager.CacheManager;
import com.zhaofujun.nest.manager.RepositoryManager;
import com.zhaofujun.nest.utils.EntityUtil;
import com.zhaofujun.nest.utils.cache.CacheClient;

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
}
