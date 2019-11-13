package com.zhaofujun.nest.core;

import java.io.Serializable;

public abstract class Identifier implements Serializable {

    public abstract String toValue();
//    public abstract Identifier valueOf(String value);

    @Override
    public String toString() {
        return toValue();
    }

    @Override
    public boolean equals(Object obj) {
        if (Identifier.class.isAssignableFrom(obj.getClass())) {
            Identifier identifier = (Identifier) obj;
            return this.toValue().equals(identifier.toValue());
        }
        return false;
    }
}
