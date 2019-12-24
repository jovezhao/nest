package com.zhaofujun.nest.json;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class NestParameterizedType implements ParameterizedType {
    private Type[] actualTypeArguments;
    private Type rawType;
    private Type ownerType;

    private NestParameterizedType(Type rawType, Type[] actualTypeArguments, Type ownerType) {
        this.actualTypeArguments = actualTypeArguments;
        this.rawType = rawType;
        this.ownerType = ownerType;
    }

    @Override
    public Type[] getActualTypeArguments() {
        return this.actualTypeArguments;
    }

    @Override
    public Type getRawType() {
        return this.rawType;
    }

    @Override
    public Type getOwnerType() {
        return this.ownerType;
    }

    public static ParameterizedType make(Class<?> rawType, Type... actualTypeArguments) {
        return new NestParameterizedType(rawType, actualTypeArguments, null);
    }

    public static ParameterizedType make(Class<?> rawType, Type[] actualTypeArguments, Type ownerType) {
        return new NestParameterizedType(rawType, actualTypeArguments, ownerType);
    }
}
