package com.zhaofujun.nest.context.loader;


import com.zhaofujun.nest.context.ServiceContext;
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
        U u = EntityUtils.create(uClass, id, true, false);

        //新对象加入上下文中
        ServiceContext serviceContext = ServiceContext.getCurrent();
        if (serviceContext != null) {
            serviceContext.getContextUnitOfWork().addEntityObject(u);
        }
        return u;
    }


}
