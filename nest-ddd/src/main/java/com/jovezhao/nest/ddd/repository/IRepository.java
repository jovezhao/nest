package com.jovezhao.nest.ddd.repository;

import com.jovezhao.nest.ddd.BaseEntityObject;
import com.jovezhao.nest.ddd.Identifier;
import com.jovezhao.nest.ddd.builder.EntityLoader;

/**
 * 实体仓储定义
 * Created by Jove on 2016/7/26.
 */
public interface IRepository<T extends BaseEntityObject> {

    /**
     * 通过id加载一个实体
     *
     * @param id
     * @return
     */
    T getEntityById(Identifier id, EntityLoader<T> builder);

    /**
     * 保存一个实体，如果已经存在的情况下覆盖已存在的内容
     *
     * @param t
     */
    void save(T t);

    void remove(T t);
}

