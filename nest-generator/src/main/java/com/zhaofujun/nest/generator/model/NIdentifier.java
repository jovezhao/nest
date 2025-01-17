package com.zhaofujun.nest.generator.model;

import lombok.Getter;

@Getter
public class NIdentifier extends NValueObject{

    public NIdentifier(String packageName, String name ) {
        super(packageName, name);
    }
}
