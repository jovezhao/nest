package com.zhaofujun.nest3.application.loader;


import com.zhaofujun.nest3.application.NestApplication;
import com.zhaofujun.nest3.application.context.ServiceContext;
import com.zhaofujun.nest3.model.AggregateRoot;
import com.zhaofujun.nest3.model.Entity;
import com.zhaofujun.nest3.utils.EntityUtils;

import java.lang.reflect.InvocationTargetException;

public class EntityCreate {

    public static <T extends Entity> T create(Class tClass, boolean newInstance) {
        if (AggregateRoot.class.isAssignableFrom(tClass)) {
            T t = null;
            try {
                t = (T) tClass.getConstructor().newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            EntityUtils.setValue(AggregateRoot.class, t, "__new", newInstance);

            return t;
        } else {
            return null;
        }
    }

//    public static <T extends BaseEntity> T create(Class tClass, AbstractIdentifier identifier, boolean newInstance, boolean loading) {
//        T entity = create(tClass, newInstance);
//        EntityUtils.setIdentifier(entity, identifier);
//        return entity;
//    }
}
