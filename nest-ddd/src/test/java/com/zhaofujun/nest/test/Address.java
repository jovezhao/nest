package com.zhaofujun.nest.test;

import com.zhaofujun.nest.core.ValueObject;

public class Address extends ValueObject {
    private int x;
    private int y;
    private User user;

    public Address(int x, int y, User user) {
        this.x = x;
        this.y = y;
        this.user = user;
    }
}
