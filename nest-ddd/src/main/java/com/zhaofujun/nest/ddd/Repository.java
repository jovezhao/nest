package com.zhaofujun.nest.ddd;

import java.util.Collection;

public interface Repository<T extends Entity> {

    Class<T> getEntityClass();

    T getEntityById(Class tClass, Identifier identifier);

    void insert(T t);

    void update(T t);

    void delete(T t);

    default void batchInsert(Collection<T> ts) {
        for (T t : ts) {
            insert(t);
        }
    }

    default void batchUpdate(Collection<T> ts) {
        for (T t : ts) {
            update(t);
        }
    }

    default void batchDelete(Collection<T> ts) {
        for (T t : ts) {
            delete(t);
        }
    }


}