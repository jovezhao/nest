package com.zhaofujun.nest.ddd;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

/**
 * 值对象抽象类，继承自 DomainObject 类。
 */
public abstract class ValueObject extends DomainObject implements Serializable {
    protected abstract Object[] getPropertiesForComparison();

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || !(o instanceof ValueObject))
            return false;

        ValueObject other = (ValueObject) o;

        Object[] thisProperties = this.getPropertiesForComparison();
        Object[] otherProperties = other.getPropertiesForComparison();
        if (thisProperties.length != otherProperties.length)
            return false;
        for (int i = 0; i < thisProperties.length; i++) {
            if (!Objects.equals(thisProperties[i], otherProperties[i]))
                return false;
        }
        return true;

    }

    @Override
    public int hashCode() {
        Object[] properties = this.getPropertiesForComparison();
        return Arrays.hashCode(properties);
    }
}