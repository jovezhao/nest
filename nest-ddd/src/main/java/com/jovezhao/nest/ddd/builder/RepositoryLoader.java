package com.jovezhao.nest.ddd.builder;

import com.jovezhao.nest.ddd.*;
import com.jovezhao.nest.ddd.repository.EntityCacheManager;
import com.jovezhao.nest.ddd.repository.IRepository;
import com.jovezhao.nest.ddd.repository.RepositoryManager;

/**
 * 如果加载一个角色时请使用RoleRepositoryLoader，否则不能加载伴演者相关信息
 * Created by Jove on 2016/8/30.
 */
public class RepositoryLoader<T extends BaseEntityObject> implements EntityLoader<T> {

    private Class<T> tClass;

    public RepositoryLoader(Class<T> tClass) {
        this.tClass = tClass;
    }

    @Override
    public T create(Identifier id) {
        //先查询缓存中是否存在，如果不存在再从仓储获取
        T t = EntityCacheManager.get(tClass, id);
        if (t == null) {
            IRepository<T> repository = RepositoryManager.getEntityRepository(tClass);
            t = repository.getEntityById(id, new RepositoryPreloader<T>(tClass));
            if (t != null) {
                EntityObjectUtils.endLoad(t);
                EntityCacheManager.put(t);
            }
        }


        return t;
    }

    @Override
    public <U extends T> U create(Class<U> uClass, Identifier id) {
        //先查询缓存中是否存在，如果不存在再从仓储获取
        U u = EntityCacheManager.get(uClass, id);
        if (u == null) {
            IRepository<U> repository = RepositoryManager.getEntityRepository(tClass);
            u = repository.getEntityById(id, new RepositoryPreloader<U>(uClass));
            if (u != null) {
                EntityObjectUtils.endLoad(u);
                EntityCacheManager.put(u);
            }
        }

        return u;
    }
}

