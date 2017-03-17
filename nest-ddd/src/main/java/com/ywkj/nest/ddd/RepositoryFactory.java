package com.ywkj.nest.ddd;

import com.ywkj.nest.core.utils.SpringUtils;

/**
 * Created by Jove on 2017/1/9.
 */
class RepositoryFactory {
    private static String getRepName(Class tClass) {
        return tClass.getSimpleName().split("\\$")[0] + "_Repository";
    }

    public static IRepository createEntityRepository(Class clazz) {
        if (clazz.equals(EntityObject.class)) return null;
        String repName = getRepName(clazz);
        IRepository repository = SpringUtils.getInstance(IRepository.class, repName);
        if (repository == null)
            return createEntityRepository(clazz.getSuperclass());
        return repository;
    }

//    public static IRoleRepository createRoleRepository(Class clazz) {
//        if (clazz.equals(EntityObject.class)) return null;
//        String repName = getRepName(clazz);
//        IRoleRepository repository = SpringUtils.getInstance(IRoleRepository.class, repName);
//        if (repository == null)
//            return createRoleRepository(clazz.getSuperclass());
//        return repository;
//    }
}
