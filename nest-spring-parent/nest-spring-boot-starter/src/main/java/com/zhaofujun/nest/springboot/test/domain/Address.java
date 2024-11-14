package com.zhaofujun.nest.springboot.test.domain;

import com.zhaofujun.nest.ddd.ValueObject;

public  class Address extends ValueObject {
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
