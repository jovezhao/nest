package com.zhaofujun.nest.context.model;

import com.zhaofujun.nest.context.model.Entity;
import com.zhaofujun.nest.core.Identifier;

public abstract class Role<A extends Entity,U extends Identifier> extends Entity<U> {
    private A actor;

    public A getActor() {
        return actor;
    }


}