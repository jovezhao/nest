package com.ywkj.nest.mybatis;

import com.ywkj.nest.core.utils.ArrayUtils;
import com.ywkj.nest.ddd.EntityObject;
import com.ywkj.nest.ddd.EntityObjectFactory;
import org.apache.ibatis.reflection.factory.ObjectFactory;

import java.util.Collection;
import java.util.List;
import java.util.Properties;

/**
 * Created by Jove on 2017/1/9.
 */
public class NestObjectFactory implements ObjectFactory {

    @Override
    public void setProperties(Properties properties) {

    }

    @Override
    public <T> T create(Class<T> type) {
        return create(type, null, null);
    }


    public <T> T create(Class<T> type, List<Class<?>> constructorArgTypes, List<Object> constructorArgs) {
        T t = EntityObjectFactory.create(type, constructorArgTypes.toArray(new Class[constructorArgTypes.size()]), constructorArgs.toArray(new Object[constructorArgs.size()]));

        return t;
    }

    @Override
    public <T> boolean isCollection(Class<T> type) {
        return Collection.class.isAssignableFrom(type);
    }
}
