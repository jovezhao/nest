package com.ywkj.nest.mybatis;

import com.ywkj.nest.ddd.EntityObject;
import com.ywkj.nest.ddd.EntityObjectFactory;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by Jove on 2017/1/9.
 */
public class NestObjectFactory extends DefaultObjectFactory {

    @Override
    public <T> T create(Class<T> type, List<Class<?>> constructorArgTypes, List<Object> constructorArgs) {

        if (EntityObject.class.isAssignableFrom(type)) {
            Class[] arrayClazz = null;
            if (constructorArgTypes != null) {
                arrayClazz = constructorArgTypes.toArray(new Class[constructorArgTypes.size()]);
            }
            Object[] arrayObject = null;
            if (constructorArgs != null)
                arrayObject = constructorArgs.toArray(new Object[constructorArgs.size()]);

            T t = EntityObjectFactory.create(type, arrayClazz, arrayObject);
            try {
                Field field=EntityObject.class.getDeclaredField("isLoad");
                field.setAccessible(true);
                field.set(t,true);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return t;
        } else {
            return super.create(type, constructorArgTypes, constructorArgs);
        }
    }
//    @Override
//    public void setProperties(Properties properties) {
//
//    }
//
//    @Override
//    public <T> T create(Class<T> type) {
//        return create(type, null, null);
//    }
//
//
//    public <T> T create(Class<T> type, List<Class<?>> constructorArgTypes, List<Object> constructorArgs) {
//        T t = EntityObjectFactory.create(type, constructorArgTypes.toArray(new Class[constructorArgTypes.size()]), constructorArgs.toArray(new Object[constructorArgs.size()]));
//
//        return t;
//    }
//
//    @Override
//    public <T> boolean isCollection(Class<T> type) {
//        return Collection.class.isAssignableFrom(type);
//    }
}
