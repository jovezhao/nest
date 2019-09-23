package com.zhaofujun.nest.context.repository;

import com.zhaofujun.nest.context.loader.EntityLoader;
import com.zhaofujun.nest.context.model.BaseEntity;
import com.zhaofujun.nest.context.model.Identifier;

public interface Repository<T extends BaseEntity> {

    Class<T> getEntityClass();

    T getEntityById(Identifier identifier, EntityLoader<T> entityLoader);

    void save(T t);

    void remove(T t);


}
//public @interface Repository1{
//
//}