package com.zhaofujun.nest.context.model;

import com.zhaofujun.nest.standard.Identifier;

import java.io.Serializable;

public abstract class AbstractIdentifier implements Identifier, Serializable {

    public abstract String toValue();
//    public abstract AbstractIdentifier valueOf(String value);

    @Override
    public String toString() {
        return toValue();
    }

    @Override
    public boolean equals(Object obj) {
        if (AbstractIdentifier.class.isInstance(obj)) {
            AbstractIdentifier abstractIdentifier = (AbstractIdentifier) obj;
            return this.toValue().equals(abstractIdentifier.toValue());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return ("!@#$%^" + toValue() + "(*&").hashCode();
    }
}
