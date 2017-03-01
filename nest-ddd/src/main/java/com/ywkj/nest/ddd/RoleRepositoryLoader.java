package com.ywkj.nest.ddd;

/**
 * Created by Jove on 2017/2/6.
 */
public class RoleRepositoryLoader<T extends AbstractRole<U>, U extends EntityObject> implements IRoleBuilder<T, U> {


    @Override
    public T build(Class<T> tClass, Class<U> uClass, String id) {
        //先查询缓存中是否存在，如果不存在再从仓储获取
        T t = EntityObjectCacheManager.get(tClass, id);
        if (t == null) {
            IRoleRepository<T> repository = RepositoryFactory.createRoleRepository(tClass);
            if (repository != null) {
                String actorId = repository.getActorIdByRoleId(id);

                IBuilder<U> builder = new RepositoryLoader<>(uClass);
                U u = builder.build(actorId);
                t = u.act(tClass, id);
                EntityObjectCacheManager.put(t);
            }
        }


        return t;
    }
}
