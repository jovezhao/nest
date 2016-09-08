package com.ywkj.nest.ddd;

import java.util.Set;

/**
 * 实体仓储定义
 * Created by Jove on 2016/7/26.
 */
public interface IRepository<T extends EntityObject> {

    /**
     * 通过id加载一个实体
     *
     * @param id
     * @return
     */
    T getEntityById(String id);

    /**
     * 保存一个实体，如果已经存在的情况下覆盖已存在的内容
     *
     * @param t
     */
    void save(T t);
    void remove(T t);
}

