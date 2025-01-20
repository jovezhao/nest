package com.zhaofujun.nest.generator.model;

import lombok.Getter;

/**
 * 定义类型
 */
@Getter
public abstract class NClass {
    private final String packageName;
    private final String name;

    public NClass(String packageName, String name) {
        this.packageName = packageName;
        this.name = name;
    }
}