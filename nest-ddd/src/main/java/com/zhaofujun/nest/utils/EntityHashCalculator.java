package com.zhaofujun.nest.utils;

import com.zhaofujun.nest.context.model.BaseEntity;
import com.zhaofujun.nest.context.model.BaseValueObject;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;


public class EntityHashCalculator {
    private static Map<Class, List<Field>> cacheFieldMap = new HashMap<>();

    public int getEntityHash(Object obj) {
        if (obj == null) return 0;

        return getEntityHash(obj, obj.getClass());
    }

    public int getEntityHash(Object obj, Class tClass) {
        if (obj == null) return 0;

        AtomicInteger result = new AtomicInteger();

        if (Collection.class.isAssignableFrom(tClass)) {
            Collection collection = (Collection) obj;
            collection.forEach(p -> result.addAndGet(getEntityHash(p)));
            return result.get();
        }

        List<Field> entityFields = getEntityFields(tClass);
        entityFields.forEach(field -> {
            field.setAccessible(true);
            Object fieldValue = null;
            try {
                fieldValue = field.get(obj);
            } catch (IllegalAccessException e) {

            }
            if (fieldValue != null) {
                Class<?> fieldType = field.getType();
                String fieldName = field.getName();
                if (BaseEntity.class.isAssignableFrom(fieldType)) {
                    //如果是实体，获取实体的标识转换的hashCode
                    BaseEntity entity = (BaseEntity) fieldValue;
                    if (entity.getId() != null)
                        result.addAndGet(getFieldHashCode(fieldName, entity.getId().hashCode()));
                } else if (BaseValueObject.class.isAssignableFrom(fieldType)) {
                    result.addAndGet(getFieldHashCode(fieldName, getEntityHash(fieldValue)));
                } else if (Collection.class.isAssignableFrom(fieldType)) {
                    result.addAndGet(getFieldHashCode(fieldName, getEntityHash(fieldValue)));
                } else {
                    result.addAndGet(getFieldHashCode(fieldName, fieldValue.hashCode()));
                }
            }
        });

        return result.get();
    }


    private int getFieldHashCode(String fieldName, int hashCode) {
        return (fieldName + hashCode + fieldName).hashCode();
    }

    private List<Field> getEntityFields(Class tClass) {
        List<Field> result = cacheFieldMap.get(tClass);
        if (result != null) return result;

        List<Field> fieldList = new ArrayList<>();
        Class aClass = tClass;
        while (aClass != null) {
            Field[] fields = aClass.getDeclaredFields();
            fieldList.addAll(Arrays.asList(fields)
                    .stream()
                    .filter(p -> !Modifier.isStatic(p.getModifiers()))
                    .filter(p -> !Modifier.isTransient(p.getModifiers()))
                    .collect(Collectors.toList())
            );
            aClass = aClass.getSuperclass();
        }
        result = fieldList;

        cacheFieldMap.put(tClass, fieldList);
        return result;
    }


}
