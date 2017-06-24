package com.jovezhao.nest.ddd.repository;

import com.jovezhao.nest.ddd.BaseEntityObject;
import com.jovezhao.nest.ddd.event.provider.distribut.DistributedEventInfo;

/**
 * Created by Jove on 2016/9/28.
 */
public interface IUnitOfWork {

    void addEntityObject(BaseEntityObject entityObject);

    void removeEntityObject(BaseEntityObject entityObject);

    void addEvent(DistributedEventInfo distributedEventInfo);


    void commit();
    void rollback();
}
