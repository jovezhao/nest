package com.zhaofujun.nest.context.model;

public class StringIdentifier extends AbstractIdentifier {

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

    public static StringIdentifier valueOf(String value) {
        return new StringIdentifier(value);
    }
}

