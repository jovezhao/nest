package com.zhaofujun.nest.standard;


import java.util.List;

public interface Repository<T extends Entity> {

    Class<T> getEntityClass();

    T getEntityById(Identifier abstractIdentifier, EntityLoader<T> entityLoader);

    void insert(T t);

    void update(T t);

    void delete(T t);


    default void batchInsert(List<T> ts) {
        for (T t : ts) {
            insert(t);
        }
    }

    default void batchUpdate(List<T> ts) {
        for (T t : ts) {
            update(t);
        }
    }

    default void batchDelete(List<T> ts) {
        for (T t : ts) {
            delete(t);
        }
    }
}
