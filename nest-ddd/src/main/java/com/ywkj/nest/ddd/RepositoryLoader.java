package com.ywkj.nest.ddd;

import com.ywkj.nest.core.utils.SpringUtils;

/**
 * Created by Jove on 2016/8/30.
 */
public class RepositoryLoader<T extends EntityObject> implements IBuilder<T> {
    private String id;

    public RepositoryLoader(String id) {
        this.id = id;
    }


    @Override
    public T build(Class<T> tClass) {
        //先查询缓存中是否存在，如果不正在再从仓储获取
        T t = EntityObjectCacheManager.get(tClass, id);
        if (t == null) {

            IRepository<T> repository = RepositoryFactory.createEntityRepository(tClass);
            if (repository != null) {
                t = repository.getEntityById(id);
                EntityObjectCacheManager.put(t);
            }
        }


        return t;
    }
}
