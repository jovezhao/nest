package com.jovezhao.nest.ddd;

/**
 * Created by Jove on 2016/9/28.
 */
public interface IUnitOfWork {
    void commit();

    void addEntityObject(EntityObject entityObject);

    void removeEntityObject(EntityObject entityObject);
    void rollback();
}
