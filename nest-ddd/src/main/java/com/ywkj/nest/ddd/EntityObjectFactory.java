package com.ywkj.nest.ddd;

import com.ywkj.nest.core.identifier.IdentifierGenerator;
import com.ywkj.nest.core.utils.MapUtils;

/**
 * 实体工厂
 * Created by Jove on 2017/1/9.
 */
public class EntityObjectFactory {
    public static <T> T create(Class<T> tClass) {
        return create(tClass, null, null);
    }

    public static <T> T create(Class<T> tClass, Class[] constructorArgTypes, Object[] constructorArgs) {
        EntityObjectMethodInterceptor proxy = new EntityObjectMethodInterceptor();
        T proxyImp = (T) proxy.getProxy(tClass, constructorArgTypes, constructorArgs);
        return proxyImp;
    }

    public static <T extends EntityObject> T createByDto(Class<T> tClass, Object dto, String id) {
        String eid = id;
        T t = null;
        if (org.springframework.util.StringUtils.isEmpty(id)) {
            IdentifierGenerator generator = new IdentifierGenerator();
            eid = generator.generate(tClass);
            t = EntityObjectFactory.create(tClass);
        } else {
            IBuilder<T> builder = new RepositoryLoader<>(eid);
            t = builder.build(tClass);
        }
        MapUtils.map(dto, t);
        return t;
    }
}
