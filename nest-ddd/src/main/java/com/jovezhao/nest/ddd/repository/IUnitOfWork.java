package com.jovezhao.nest.ddd.repository;

import com.jovezhao.nest.ddd.BaseEntityObject;

/**
 * Created by Jove on 2016/9/28.
 */
public interface IUnitOfWork {
    void commit();

    void addEntityObject(BaseEntityObject entityObject);

    void removeEntityObject(BaseEntityObject entityObject);
    void rollback();
}
