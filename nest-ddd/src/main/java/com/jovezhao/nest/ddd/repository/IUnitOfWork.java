package com.jovezhao.nest.ddd.repository;

import com.jovezhao.nest.ddd.BaseEntityObject;
import com.jovezhao.nest.ddd.event.provider.distribut.EventData;

/**
 * Created by Jove on 2016/9/28.
 */
public interface IUnitOfWork {
    void entityCommit();

    void addEntityObject(BaseEntityObject entityObject);

    void removeEntityObject(BaseEntityObject entityObject);

    void addEvent(EventData eventData);

    void eventCommit();

    void rollback();
}
