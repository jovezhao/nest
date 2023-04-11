package com.zhaofujun.nest3.model;

public abstract class ValueObject extends DomainObject {
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ValueObject)) return false;
        if (!this.getClass().equals(obj.getClass())) return false;

        return this.hashCode() == obj.hashCode();
    }
}
