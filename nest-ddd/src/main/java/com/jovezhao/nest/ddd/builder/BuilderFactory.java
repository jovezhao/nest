package com.jovezhao.nest.ddd.builder;


import com.jovezhao.nest.ddd.BaseEntityObject;

/**
 * 用于创建各种builder
 * Created by zhaofujun on 2017/3/20.
 */
public class BuilderFactory {

    public static <T extends BaseEntityObject> IBuilder<T> createForLoad(Class<T> tClass) {
        return new RepositoryLoader<T>(tClass);
    }

    public static <T extends BaseEntityObject> IBuilder<T> createForCreate(Class<T> tClass) {
        return new FactoryBuilder<T>(tClass);
    }
}
