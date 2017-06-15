package com.jovezhao.nest.ddd.builder;

import com.jovezhao.nest.ddd.EntityObject;

/**
 * 用于创建各种builder
 * Created by zhaofujun on 2017/3/20.
 */
public class BuilderFactory {

    public static <T extends EntityObject> IBuilder<T> createForLoad(Class<T> tClass) {
        return new RepositoryLoader<T>(tClass);
    }

    public static <T extends EntityObject> IBuilder<T> createForCreate(Class<T> tClass) {
        return new FactoryBuilder<T>(tClass);
    }
}
