package com.ywkj.nest.ddd;

/**
 * 领域实体构建器
 * Created by Jove on 2016/8/30.
 */
public interface IBuilder<T extends EntityObject> {
    T build(Class<T> tClass, String id);
}
