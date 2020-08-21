package com.zhaofujun.nest.test.domain;

import com.zhaofujun.nest.context.model.BaseValueObject;

public abstract class Address extends BaseValueObject {
    private int x;
    private int y;
    private String name;

    public Address(int x, int y, String name) {
        this.x = x;
        this.y = y;
        this.name = name;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getName() {
        return name;
    }
}
