package com.zhaofujun.nest.json;

import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class ParameterizedTypeFactory {

    public static ParameterizedType make(Class<?> rawType, Type... actualTypeArguments) {
        return ParameterizedTypeImpl.make(rawType, actualTypeArguments, null);
    }

    public static ParameterizedType make(Class<?> rawType,
                                         Type[] actualTypeArguments,
                                         Type ownerType) {
        return ParameterizedTypeImpl.make(rawType, actualTypeArguments, ownerType);
    }

}
