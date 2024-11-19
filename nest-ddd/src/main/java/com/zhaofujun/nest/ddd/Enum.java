package com.zhaofujun.nest.ddd;

/**
 * 表示枚举的接口。
 * 枚举是一种特殊的类型，它代表了一组命名的值。
 */
public interface Enum<T> {

    /**
     * 获取枚举的标签。
     * 
     * @return 枚举的标签。
     */
    String getLabel();

    /**
     * 获取枚举的值。
     * 
     * @return 枚举的值。
     */
    T getValue();

    /**
     * 获取枚举的键。
     * 
     * @return 枚举的键。
     */
    String getKey();
}
