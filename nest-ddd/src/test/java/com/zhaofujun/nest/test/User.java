package com.zhaofujun.nest.test;

import com.zhaofujun.nest.ddd.Entity;
import com.zhaofujun.nest.ddd.StringIdentifier;

public class User extends Entity<StringIdentifier> {
    public User(StringIdentifier identifier) {
        super(identifier);
    }

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User [name=" + name + ", id=" + id.toValue() + "]"+this.hashCode();
    }

    
}