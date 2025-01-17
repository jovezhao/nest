package com.zhaofujun.nest.generator.model;

import lombok.Getter;

@Getter
public class  NEntity extends NModel{
    private final NIdentifier nIdentifier;
    private final boolean _abstract;

    public NEntity(String packageName, String name, NIdentifier nIdentifier, boolean _abstract) {
        super(packageName, name);
        this.nIdentifier = nIdentifier;
        this._abstract = _abstract;
    }
}
