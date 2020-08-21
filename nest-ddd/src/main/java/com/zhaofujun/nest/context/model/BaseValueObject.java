package com.zhaofujun.nest.context.model;

import com.zhaofujun.nest.standard.ValueObject;
import com.zhaofujun.nest.utils.EntityHashCalculator;

import java.io.Serializable;

public abstract class BaseValueObject implements ValueObject, Serializable {
    @Override
    public int hashCode() {
        EntityHashCalculator entityHashCalculator = new EntityHashCalculator();
        return entityHashCalculator.getEntityHash(this);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof BaseValueObject)) return false;
        if (!this.getClass().equals(obj.getClass())) return false;

        return this.hashCode() == obj.hashCode();
    }
}
