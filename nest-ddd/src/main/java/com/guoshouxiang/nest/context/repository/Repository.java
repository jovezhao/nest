package com.guoshouxiang.nest.context.repository;

import com.guoshouxiang.nest.context.loader.EntityLoader;
import com.guoshouxiang.nest.context.model.BaseEntity;
import com.guoshouxiang.nest.context.model.Identifier;

public interface Repository<T extends BaseEntity> {

    Class<T> getEntityClass();

    T getEntityById(Identifier identifier, EntityLoader<T> entityLoader);

    void save(T t);

    void remove(T t);


}
//public @interface Repository1{
//
//}