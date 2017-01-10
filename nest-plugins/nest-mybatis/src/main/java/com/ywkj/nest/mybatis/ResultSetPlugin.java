package com.ywkj.nest.mybatis;

import com.ywkj.nest.ddd.EntityObject;
import org.apache.ibatis.executor.resultset.DefaultResultSetHandler;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;

import java.lang.reflect.Field;
import java.sql.Statement;
import java.util.List;
import java.util.Properties;

/**
 * Created by Jove on 2017/1/9.
 */
@Intercepts({@Signature(
        type = ResultSetHandler.class,
        method = "handleResultSets",
        args = {Statement.class})})
public class ResultSetPlugin implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {


        List objects = (List) invocation.proceed();
        for (Object obj : objects) {
            if (EntityObject.class.isAssignableFrom(obj.getClass())) {
                Field field = EntityObject.class.getDeclaredField("isLoad");
                field.setAccessible(true);
                field.set(obj, false);

            }

        }


        return objects;
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
