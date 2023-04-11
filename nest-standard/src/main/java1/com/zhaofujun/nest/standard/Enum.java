package com.zhaofujun.nest.standard;

public interface Enum<T> {

    String getLabel();

    T getValue();

    String getKey();
}
