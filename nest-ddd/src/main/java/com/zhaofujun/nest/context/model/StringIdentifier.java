package com.zhaofujun.nest.context.model;

import com.zhaofujun.nest.core.Identifier;

public class StringIdentifier extends Identifier {

    private String id;


    public StringIdentifier(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String toValue() {
        return getId();
    }

    public static Identifier valueOf(String value) {
        return new StringIdentifier(value);
    }
}

