package com.zhaofujun.nest.core;

import com.zhaofujun.nest.context.loader.ConstructEntityLoader;
import com.zhaofujun.nest.context.loader.RepositoryEntityLoader;

import java.lang.reflect.Type;


public class EntityFactory {
    public static <T extends BaseEntity> T create(Class<T> tClass, Identifier identifier) {
        EntityLoader<T> entityLoader = new ConstructEntityLoader<>(tClass);// EntityLoaderFactory.create(tClass, EntityLoaderType.ConstructEntityLoader);
        T t = entityLoader.create(identifier);
        return t;
    }

    public static <T extends BaseEntity> T load(Class<T> tClass, Identifier identifier) {
        EntityLoader<T> entityLoader = new RepositoryEntityLoader<>(tClass);// EntityLoaderFactory.create(tClass, EntityLoaderType.RepositoryEntityLoader);
        T t = entityLoader.create(identifier);
        return t;
    }

    public static <T extends BaseEntity> T createOrLoad(Class<T> tClass, Identifier identifier) {
        T t = load(tClass, identifier);
        if (t == null) {
            t = create(tClass, identifier);
        }
        return t;
    }
}
