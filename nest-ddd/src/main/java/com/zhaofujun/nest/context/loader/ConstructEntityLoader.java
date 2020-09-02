package com.zhaofujun.nest.context.loader;

import com.zhaofujun.nest.context.model.BaseEntity;
import com.zhaofujun.nest.standard.EntityLoader;
import com.zhaofujun.nest.standard.Identifier;
import com.zhaofujun.nest.utils.EntityUtils;

import java.io.Serializable;

//无中生有类实体的构建
public class ConstructEntityLoader<T extends BaseEntity> implements EntityLoader<T>, Serializable {
    private Class tClass;

    public ConstructEntityLoader(Class tClass) {
        this.tClass = tClass;
    }

    public T create(Identifier id) {
        return create(tClass, id);
    }

    public <U extends T> U create(Class uClass, Identifier id) {
        U u = EntityCreate.create(uClass, true);
        EntityUtils.setIdentifier(u, id);
        u.ready();
        return u;
    }


}
