package com.zhaofujun.nest.core;


import com.zhaofujun.nest.context.model.Entity;

import java.util.List;

public interface Repository<T extends Entity> {

    Class<T> getEntityClass();

    T getEntityById(Identifier identifier, EntityLoader<T> entityLoader);

    void insert(T t);

    void update(T t);

    void delete(T t);


    default void batchInsert(List<T> ts) {
        for (T t : ts) {
            t.verify();
            insert(t);
        }
    }

    default void batchUpdate(List<T> ts) {
        for (T t : ts) {
            t.verify();
            update(t);
        }
    }

    default void batchDelete(List<T> ts) {
        for (T t : ts) {
            delete(t);
        }
    }
}
