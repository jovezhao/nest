package com.zhaofujun.nest3.application.provider;

import java.lang.reflect.Type;

public interface JsonProvider<T> extends Provider {
    String toJsonString(T object);

     T parseObject(String text, Type type);
}
