package com.jovezhao.nest.ddd.repository;

import com.jovezhao.nest.ddd.BaseEntityObject;
import com.jovezhao.nest.exception.SystemException;
import com.jovezhao.nest.utils.SpringUtils;

/**
 * Created by Jove on 2017/1/9.
 */
public class RepositoryManager {
    private static String getRepName(Class tClass) {
        return tClass.getSimpleName().split("\\$")[0] + "_Repository";
    }

    public static IRepository getEntityRepository(Class clazz) {
        if (clazz.equals(BaseEntityObject.class)) return null;
        String repName = getRepName(clazz);
        IRepository repository = SpringUtils.getInstance(IRepository.class, repName);
        if (repository == null)
            return getEntityRepository(clazz.getSuperclass());

        if(repository==null)
            throw new SystemException("没有找到"+clazz.getName()+"对应的仓储bean");
        return repository;
    }

}
