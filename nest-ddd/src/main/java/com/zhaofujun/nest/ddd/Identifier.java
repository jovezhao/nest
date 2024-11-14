package com.zhaofujun.nest.ddd;

import java.io.Serializable;

public abstract class Identifier implements Serializable {
    public abstract String toValue();

    @Override
    public boolean equals(Object obj) {
        if (Identifier.class.isInstance(obj)) {
            Identifier identifier = (Identifier) obj;
            return this.toValue().equals(identifier.toValue());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return ("!@#$%^" + toValue() + "(*&").hashCode();
    }
}
