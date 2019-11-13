package com.zhaofujun.nest.core;

public abstract class BaseRole<A extends BaseEntity,U extends Identifier> extends BaseEntity<U> {
    private A actor;

    public A getActor() {
        return actor;
    }


}