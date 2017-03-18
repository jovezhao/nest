package com.ywkj.nest.ddd.builder;

import com.ywkj.nest.ddd.*;

/**
 * 如果加载一个角色时请使用RoleRepositoryLoader，否则不能加载伴演者相关信息
 * Created by Jove on 2016/8/30.
 */
public class RepositoryLoader<T extends EntityObject> implements IBuilder<T> {

    private Class<T> tClass;

    public RepositoryLoader(Class<T> tClass) {
        this.tClass = tClass;
    }

    @Override
    public T build(String id) {
        //先查询缓存中是否存在，如果不存在再从仓储获取
        T t = EntityObjectCacheManager.get(tClass, id);
        if (t == null) {
            IRepository<T> repository = RepositoryManager.getEntityRepository(tClass);
            t = repository.getEntityById(id, new PreLoadBuilder<T>(tClass));
            if (t != null) {
                EntityObjectFactory.endLoad(t);
                EntityObjectCacheManager.put(t);
            }
        }


        return t;
    }
}

