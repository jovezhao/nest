package com.zhaofujun.nest3.application.loader;

import com.zhaofujun.nest3.NestCustomException;
import com.zhaofujun.nest3.model.AggregateRoot;
import com.zhaofujun.nest3.model.EntityLoader;
import com.zhaofujun.nest3.model.Identifier;
import com.zhaofujun.nest3.utils.EntityUtils;

import java.io.Serializable;

//无中生有类实体的构建
public class ConstructEntityLoader<T extends AggregateRoot> implements EntityLoader<T>, Serializable {
    private Class<T> tClass;

    public ConstructEntityLoader(Class<T> tClass) {
        this.tClass = tClass;
    }

    public T create(Identifier id) {
        return create(tClass, id);
    }

    public <U extends T> U create(Class<U> uClass, Identifier id) {
        //从当前上下文中获取实体
        EntityLoader<U> entityLoader = new UnitOfWorkEntityLoader<>(uClass);
        U result = entityLoader.create(id);
        if (result != null) {
            throw new NestCustomException(NestCustomException.EntityExisted, "实体已存在上下文中");
        }

        U u = EntityCreate.create(uClass, true);
        EntityUtils.setIdentifier(u, id);

        u.attach();
        return u;
    }


}
