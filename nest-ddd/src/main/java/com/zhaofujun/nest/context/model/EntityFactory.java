package com.zhaofujun.nest.context.model;

import com.zhaofujun.nest.context.loader.ConstructEntityLoader;
import com.zhaofujun.nest.context.loader.RepositoryEntityLoader;
import com.zhaofujun.nest.standard.Entity;
import com.zhaofujun.nest.standard.EntityLoader;
import com.zhaofujun.nest.standard.Identifier;


public class EntityFactory {
    public static <T extends Entity> T create(Class<T> tClass, Identifier identifier) {
        EntityLoader<T> entityLoader = new ConstructEntityLoader(tClass);// EntityLoaderFactory.create(tClass, EntityLoaderType.ConstructEntityLoader);
        T t = entityLoader.create(identifier);
        return t;
    }

    public static <T extends Entity> T load(Class<T> tClass, Identifier identifier) {
        EntityLoader<T> entityLoader = new RepositoryEntityLoader(tClass);// EntityLoaderFactory.create(tClass, EntityLoaderType.RepositoryEntityLoader);
        T t = entityLoader.create(identifier);
        return t;
    }

    public static <T extends Entity> T createOrLoad(Class<T> tClass, Identifier identifier) {
        T t = load(tClass, identifier);
        if (t == null) {
            t = create(tClass, identifier);
        }
        return t;
    }
}
