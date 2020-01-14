package com.zhaofujun.nest.context.loader;

import com.zhaofujun.nest.SystemException;
import com.zhaofujun.nest.context.ServiceContext;
import com.zhaofujun.nest.context.ServiceContextManager;
import com.zhaofujun.nest.context.model.Entity;
import com.zhaofujun.nest.context.model.Role;
import com.zhaofujun.nest.core.Identifier;
import com.zhaofujun.nest.utils.EntityUtils;
import net.sf.cglib.proxy.Enhancer;

public class EntityCreate {

    public static <T extends Entity> T create(Class tClass, Identifier identifier, boolean newInstance, boolean loading) {

        if (tClass.isAssignableFrom(Role.class))
            throw new SystemException("角色不能直接通过实体加载器创建，请通过实体act方法扮演");
        Enhancer enhancer = new Enhancer();
        // 设置需要创建子类的类
        enhancer.setSuperclass(tClass);
        enhancer.setCallback(new EntityMethodInterceptor());
        T t = (T) enhancer.create();
//        EntityUtils.represent(t, true);// 被代理的对象默认设置代理为true
        EntityUtils.setIdentifier(t, identifier);
        EntityUtils.setNewInstance(t, newInstance);
        EntityUtils.setLoading(t, loading);


        ServiceContext serviceContext = ServiceContextManager.getCurrent();
        if (serviceContext != null) {
            if (newInstance)
                serviceContext.getContextUnitOfWork().addEntityObject(t);
            else
                serviceContext.getContextUnitOfWork().updateEntityObject(t);
        }
        return t;
    }
}
