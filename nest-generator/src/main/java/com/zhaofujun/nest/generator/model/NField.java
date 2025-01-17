package com.zhaofujun.nest.generator.model;

import lombok.Getter;

@Getter
public class NField{
    private final String name;
    private final NClass nClass;
    public NField(String name, NClass nClass){
        this.name = name;
        this.nClass = nClass;
    }
}
