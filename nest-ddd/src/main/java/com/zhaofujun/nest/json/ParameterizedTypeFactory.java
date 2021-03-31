package com.zhaofujun.nest.json;

import com.zhaofujun.nest.standard.SystemException;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;

public class ParameterizedTypeFactory {

    public static ParameterizedType make(Class<?> rawType, Type... actualTypeArguments) {
        return NestParameterizedType.make(rawType, actualTypeArguments);
    }

    public static ParameterizedType make(Class<?> rawType,
                                         Type[] actualTypeArguments,
                                         Type ownerType) {
        return NestParameterizedType.make(rawType, actualTypeArguments, ownerType);
    }

}
