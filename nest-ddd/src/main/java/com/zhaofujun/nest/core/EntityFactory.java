package com.zhaofujun.nest.core;

import com.zhaofujun.nest.context.loader.EntityLoaderFactory;
import com.zhaofujun.nest.context.loader.EntityLoaderType;


public class EntityFactory {
    public static <T extends BaseEntity> T create(Class<T> tClass, Identifier identifier) {
        EntityLoader<T> entityLoader = EntityLoaderFactory.create(tClass, EntityLoaderType.ConstructEntityLoader);
        T t = entityLoader.create(identifier);
        return t;
    }

    public static <T extends BaseEntity> T load(Class<T> tClass, Identifier identifier) {
        EntityLoader<T> entityLoader = EntityLoaderFactory.create(tClass, EntityLoaderType.RepositoryEntityLoader);
        T t = entityLoader.create(identifier);
        return t;
    }
}
