package com.zhaofujun.nest.core;

import com.zhaofujun.nest.core.EntityLoader;
import com.zhaofujun.nest.core.BaseEntity;
import com.zhaofujun.nest.core.Identifier;

public interface Repository<T extends BaseEntity> {

    Class<T> getEntityClass();

    T getEntityById(Identifier identifier, EntityLoader<T> entityLoader);

    void save(T t);

    void remove(T t);


//    void batchSave(T[] t);
}
//public @interface Repository1{
//
//}