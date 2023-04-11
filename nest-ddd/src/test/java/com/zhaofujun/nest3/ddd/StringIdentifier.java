package com.zhaofujun.nest3.ddd;

import com.zhaofujun.nest3.model.Identifier;

public class StringIdentifier extends Identifier {

    private String id;

    public StringIdentifier(String id) {
        this.id = id;
    }

    @Override
    public String toValue() {
        return id.toString();
    }

    public String getId() {
        return id;
    }
}
