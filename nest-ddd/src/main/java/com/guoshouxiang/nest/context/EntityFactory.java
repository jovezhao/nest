package com.guoshouxiang.nest.context;

import com.guoshouxiang.nest.context.loader.ConstructEntityLoader;
import com.guoshouxiang.nest.context.loader.EntityLoader;
import com.guoshouxiang.nest.context.loader.RepositoryEntityLoader;
import com.guoshouxiang.nest.context.model.BaseEntity;
import com.guoshouxiang.nest.context.model.Identifier;

public class EntityFactory {
    public static   <T extends BaseEntity> T create(Class<T> tClass,Identifier identifier){
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
