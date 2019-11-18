package com.zhaofujun.nest.context.loader;

import com.zhaofujun.nest.context.model.Entity;
import com.zhaofujun.nest.core.EntityLoader;

public class EntityLoaderFactory {

    public static <T extends Entity> EntityLoader<T> create(Class<T> tClass, EntityLoaderType entityLoaderType) {
        EntityLoader<T> entityLoader = null;
        switch (entityLoaderType) {
            case CacheEntityLoader:
                entityLoader = new CacheEntityLoader<T>(tClass);
                break;
            case RepositoryEntityLoader:
                entityLoader = new RepositoryEntityLoader<>(tClass);
                break;
            case ConstructEntityLoader:
                entityLoader = new ConstructEntityLoader<>(tClass);
                break;
            case UnitOfWorkEntityLoader:
                entityLoader = new UnitOfWorkEntityLoader<>(tClass);
                break;
            case RepositoryPreEntityLoader:
                entityLoader = new RepositoryPreEntityLoader<>(tClass);
                break;
        }
        return entityLoader;
    }
}
