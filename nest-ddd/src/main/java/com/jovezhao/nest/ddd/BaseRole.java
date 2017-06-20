package com.jovezhao.nest.ddd;

public abstract class BaseRole<A extends BaseEntityObject,U extends Identifier> extends BaseEntityObject<U>{
    private A actor;

    public A getActor() {
        return actor;
    }

    void setActor(A actor) {
        this.actor = actor;
    }
}
