package com.zhaofujun.nest.ddd;

import java.lang.reflect.Type;
import java.util.Collection;

@SuppressWarnings("rawtypes")
public interface Repository<T extends Entity> {

    Type getEntityType();

    T getEntityById(Class<? extends T> tClass, Identifier identifier);

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