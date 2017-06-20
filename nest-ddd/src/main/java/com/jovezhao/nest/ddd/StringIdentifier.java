package com.jovezhao.nest.ddd;

public class StringIdentifier extends Identifier {
    private String id;

    public StringIdentifier(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return toValue();
    }

    @Override
    public String toValue() {
        return id;
    }
}
