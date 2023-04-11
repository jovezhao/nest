package com.zhaofujun.nest3.application;

import com.zhaofujun.nest3.application.loader.ConstructEntityLoader;
import com.zhaofujun.nest3.application.loader.RepositoryEntityLoader;
import com.zhaofujun.nest3.model.*;


public class AggregateRootFactory {

    public static <T extends AggregateRoot> T create(Class<T> tClass, Identifier identifier) {
        EntityLoader<T> entityLoader = new ConstructEntityLoader(tClass);
        T t = entityLoader.create(identifier);

        return t;
    }


    public static <T extends AggregateRoot> T load(Class<T> tClass, Identifier identifier) {

        EntityLoader<T> entityLoader = new RepositoryEntityLoader<>(tClass);
        T result = entityLoader.create(identifier);

        return result;
    }

    public static <T extends AggregateRoot> T createOrLoad(Class<T> tClass, Identifier identifier) {
        T t = load(tClass, identifier);
        if (t == null) {
            t = create(tClass, identifier);
        }
        return t;
    }
}

