package com.zhaofujun.nest.generator.model;

import lombok.Getter;

@Getter
public class NAggregateRoot extends NEntity{

    public NAggregateRoot(String packageName, String name , NIdentifier nIdentifier, boolean _abstract) {
        super(packageName, name, nIdentifier, _abstract);
    }
}
