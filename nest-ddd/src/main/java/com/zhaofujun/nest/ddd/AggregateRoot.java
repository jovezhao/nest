package com.zhaofujun.nest.ddd;

public abstract class AggregateRoot<T extends Identifier> extends Entity<T> {

    public AggregateRoot(T id) {
        super(id);
    }
}
