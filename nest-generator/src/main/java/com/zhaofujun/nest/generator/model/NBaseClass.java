package com.zhaofujun.nest.generator.model;

import lombok.Getter;

/**
 * 基础类型，主要包括基础类型与包装类型和字符串类型
 */
@Getter
public class NBaseClass extends NClass {

    public NBaseClass( String name) {
        super(null, name);
    }
}