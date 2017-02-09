package com.ywkj.nest.ddd;

import com.ywkj.nest.core.utils.SpringUtils;

/**
 * Created by Jove on 2017/1/9.
 */
class RepositoryFactory {
    public static <T extends EntityObject> IRepository<T> createEntityRepository(Class<T> tClass) {
        String className = tClass.getSimpleName();
        String repName = null;
        if (className.indexOf('$') > 0)
            repName = className.substring(0, className.indexOf('$')) + "_Repository";
        else
            repName = className + "_Repository";
        return SpringUtils.getInstance(IRepository.class, repName);
    }

    public static <T extends AbstractRole> IRoleRepository<T> createRoleRepository(Class<T> tClass) {
        String className = tClass.getSimpleName();
        String repName = null;
        if (className.indexOf('$') > 0)
            repName = className.substring(0, className.indexOf('$')) + "_Repository";
        else
            repName = className + "_Repository";
        return SpringUtils.getInstance(IRoleRepository.class, repName);
    }
}
