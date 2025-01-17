package com.zhaofujun.nest.generator.model;

import lombok.Getter;

@Getter
public class NValueObject extends NModel{

    public NValueObject(String packageName, String name ) {
        super(packageName, name);
    }
}
