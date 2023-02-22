package com.zhaofujun.nest.context.loader;

import com.zhaofujun.nest.context.appservice.ServiceContext;
import com.zhaofujun.nest.context.appservice.ServiceContextManager;
import com.zhaofujun.nest.context.model.BaseEntity;
import com.zhaofujun.nest.standard.SystemException;
import com.zhaofujun.nest.utils.EntityUtils;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.NoOp;

public class EntityCreate {

    public static <T extends BaseEntity> T create(Class tClass, boolean newInstance) {

        if (!BaseEntity.class.isAssignableFrom(tClass))
            throw new SystemException("Class[" + tClass.getName() + "]不是一个有效的实体");
        Enhancer enhancer = new Enhancer();
        // 设置需要创建子类的类
        enhancer.setSuperclass(tClass);
        enhancer.setCallback(NoOp.INSTANCE);
        T t = (T) enhancer.create();

        EntityUtils.setValue(BaseEntity.class, t, "__new", newInstance);


        return t;
    }

//    public static <T extends BaseEntity> T create(Class tClass, AbstractIdentifier identifier, boolean newInstance, boolean loading) {
//        T entity = create(tClass, newInstance);
//        EntityUtils.setIdentifier(entity, identifier);
//        return entity;
//    }
}
