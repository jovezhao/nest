package com.zhaofujun.nest3.model;

import java.io.Serializable;
import java.util.Objects;

public abstract class Identifier implements Serializable {

    public abstract String toValue();

    @Override
    public String toString() {
        return toValue();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        return Objects.equals(this.toValue(), ((Identifier) obj).toValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getClass(), toValue());
    }
}
