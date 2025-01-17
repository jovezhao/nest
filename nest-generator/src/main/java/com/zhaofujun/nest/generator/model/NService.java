package com.zhaofujun.nest.generator.model;

import lombok.Getter;

@Getter
public class NService extends NModel{

    public NService(String packageName, String name ) {
        super(packageName, name);
    }
}
