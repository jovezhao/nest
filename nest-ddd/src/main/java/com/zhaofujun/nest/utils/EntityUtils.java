package com.zhaofujun.nest.utils;

import com.zhaofujun.nest.context.model.BaseEntity;
import com.zhaofujun.nest.standard.DomainObject;
import com.zhaofujun.nest.standard.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;


///**
// * 实体工具
// * Created by Jove on 2017/1/9.
// */
public class EntityUtils {
    //
    private static Logger logger = LoggerFactory.getLogger(EntityUtils.class);


    public static void setValue(Class clazz, BaseEntity entityObject, String fieldName, Object value) {
        try {
            Field declaredField = clazz.getDeclaredField(fieldName);
            declaredField.setAccessible(true);
            declaredField.set(entityObject, value);
        } catch (Exception ex) {
            logger.warn("设置属性失败", ex);
        }
    }


    public static void setIdentifier(BaseEntity entityObject, Identifier abstractIdentifier) {
        setValue(BaseEntity.class, entityObject, "id", abstractIdentifier);
    }

    public static int getEntityHash(BaseEntity domainObject) {
        EntityHashCalculator calculator = new EntityHashCalculator();
        return calculator.getEntityHash(domainObject);
    }
}