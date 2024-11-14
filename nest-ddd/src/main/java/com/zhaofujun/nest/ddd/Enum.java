package com.zhaofujun.nest.ddd;

public interface Enum<T> {

    String getLabel();

    T getValue();

    String getKey();
}
