package com.zhaofujun.nest.generator.model;

import lombok.Getter;

import java.util.List;

/**
 * 定义类型
 */
@Getter
public class NClass {
    private final String packageName;
    private final String name;

    public NClass(String packageName, String name) {
        this.packageName = packageName;
        this.name = name;
    }
}

