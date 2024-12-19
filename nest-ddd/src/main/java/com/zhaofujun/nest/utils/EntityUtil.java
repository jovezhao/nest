package com.zhaofujun.nest.utils;

import com.zhaofujun.nest.ddd.AggregateRoot;
import com.zhaofujun.nest.ddd.Entity;
import com.zhaofujun.nest.ddd.Identifier;
import com.zhaofujun.nest.ddd.context.EntityLoader;

public class EntityUtil {

    public static <T extends AggregateRoot<I>, I extends Identifier> T load(Class<T> clazz, I identifier) {
        T entity = EntityLoader.load(clazz, identifier);
        return entity;
    }

    public static String getKey(Class<?> clazz, Identifier identifier) {
        final String PROXY_SPLIT_STR = "$$";
        final int BEGIN_INDEX = 0;

        String className = clazz.getName();
        int endIndex = className.indexOf(PROXY_SPLIT_STR);
        className = endIndex == -1 ? className : className.substring(BEGIN_INDEX, endIndex);
        return className + "_" + identifier.toValue();
    }

    public static String getKey(Entity entity) {
        return getKey(entity.getClass(), entity.getId());
    }

}