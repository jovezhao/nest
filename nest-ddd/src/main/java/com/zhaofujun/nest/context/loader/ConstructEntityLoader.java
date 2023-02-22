package com.zhaofujun.nest.context.loader;

import com.zhaofujun.nest.context.appservice.ServiceContext;
import com.zhaofujun.nest.context.appservice.ServiceContextManager;
import com.zhaofujun.nest.context.model.BaseEntity;
import com.zhaofujun.nest.exception.EntityExistedException;
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
        //从当前上下文中获取实体
      EntityLoader<U>  entityLoader = new UnitOfWorkEntityLoader<>(uClass);
       U result = entityLoader.create(id);
        if (result != null) {
            throw new EntityExistedException("实体"+id.toValue()+"已存在当前上下文");
        }

        U u = EntityCreate.create(uClass, true);
        EntityUtils.setIdentifier(u, id);
        ServiceContext serviceContext = ServiceContextManager.get();
        if (serviceContext != null) {
            serviceContext.put(u);
        }
        u.ready();
        return u;
    }


}
