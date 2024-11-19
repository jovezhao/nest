package com.zhaofujun.nest.ddd;

import java.lang.reflect.Type;
import java.util.Collection;

/**
 * 仓储接口，定义了对实体的基本 CRUD 操作。
 *
 * @param <T> 实体类型
 */
@SuppressWarnings("rawtypes")
public interface Repository<T extends Entity> {

    /**
     * 获取实体类型。
     *
     * @return 实体类型
     */
    Type getEntityType();

    /**
     * 根据 ID 获取实体。
     *
     * @param tClass    实体类
     * @param identifier ID
     * @return 实体
     */
    T getEntityById(Class<? extends T> tClass, Identifier identifier);

    /**
     * 插入实体。
     *
     * @param t 实体
     */
    void insert(T t);

    /**
     * 更新实体。
     *
     * @param t 实体
     */
    void update(T t);

    /**
     * 删除实体。
     *
     * @param t 实体
     */
    void delete(T t);

    /**
     * 批量插入实体。
     *
     * @param ts 实体集合
     */
    default void batchInsert(Collection<T> ts) {
        for (T t : ts) {
            insert(t);
        }
    }

    /**
     * 批量更新实体。
     *
     * @param ts 实体集合
     */
    default void batchUpdate(Collection<T> ts) {
        for (T t : ts) {
            update(t);
        }
    }

    /**
     * 批量删除实体。
     *
     * @param ts 实体集合
     */
    default void batchDelete(Collection<T> ts) {
        for (T t : ts) {
            delete(t);
        }
    }
}
