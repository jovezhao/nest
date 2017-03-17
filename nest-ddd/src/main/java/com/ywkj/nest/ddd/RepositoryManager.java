package com.ywkj.nest.ddd;

import com.ywkj.nest.core.utils.SpringUtils;

/**
 * Created by Jove on 2017/1/9.
 */
public class RepositoryManager {
    private static String getRepName(Class tClass) {
        return tClass.getSimpleName().split("\\$")[0] + "_Repository";
    }

    public static IRepository getEntityRepository(Class clazz) {
        if (clazz.equals(EntityObject.class)) return null;
        String repName = getRepName(clazz);
        IRepository repository = SpringUtils.getInstance(IRepository.class, repName);
        if (repository == null)
            return getEntityRepository(clazz.getSuperclass());
        return repository;
    }

}
