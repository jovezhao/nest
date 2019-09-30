package com.zhaofujun.nest.context;

import com.zhaofujun.nest.context.loader.ConstructEntityLoader;
import com.zhaofujun.nest.context.loader.EntityLoader;
import com.zhaofujun.nest.context.loader.RepositoryEntityLoader;
import com.zhaofujun.nest.context.model.BaseEntity;
import com.zhaofujun.nest.context.model.Identifier;

public class EntityFactory {
    public static   <T extends BaseEntity> T create(Class<T> tClass, Identifier identifier){
        EntityLoader<T> entityLoader=new ConstructEntityLoader<>(tClass);
        T t = entityLoader.create(identifier);
        return t;
    }
    public static   <T extends BaseEntity> T load(Class<T> tClass,Identifier identifier){
        EntityLoader<T> entityLoader=new RepositoryEntityLoader<>(tClass);
        T t = entityLoader.create(identifier);
        return t;
    }
}
