package com.zhaofujun.nest.context.loader;


import com.zhaofujun.nest.context.model.Entity;
import com.zhaofujun.nest.core.Identifier;
import com.zhaofujun.nest.core.EntityLoader;
import com.zhaofujun.nest.utils.EntityUtils;


import java.io.Serializable;

//无中生有类实体的构建
public class ConstructEntityLoader<T extends Entity> implements EntityLoader<T>, Serializable {
    private Class<T> tClass;

    public ConstructEntityLoader(Class<T> tClass) {
        this.tClass = tClass;
    }

    public T create(Identifier id) {
        return create(tClass, id);
    }

    public <U extends T> U create(Class<U> uClass, Identifier id) {
        return EntityUtils.create(uClass, id, true,false);
    }


}
