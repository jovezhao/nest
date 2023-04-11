package com.zhaofujun.nest3.application.event;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class RawParameterizedType implements ParameterizedType {
    private Type[] actualTypeArguments;
    private Type rawType;
    private Type ownerType;

    private RawParameterizedType(Type rawType, Type[] actualTypeArguments, Type ownerType) {
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
        return new RawParameterizedType(rawType, actualTypeArguments, null);
    }

    public static ParameterizedType make(Class<?> rawType, Type[] actualTypeArguments, Type ownerType) {
        return new RawParameterizedType(rawType, actualTypeArguments, ownerType);
    }
}
