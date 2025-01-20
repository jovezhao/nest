package com.zhaofujun.nest.generator.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class  NEntity extends NModel{
    private  NIdentifier nIdentifier;
    private  boolean _abstract;
    public NEntity(String packageName, String name) {
        super(packageName, name);
    }    
}
