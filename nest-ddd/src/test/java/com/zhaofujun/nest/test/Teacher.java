package com.zhaofujun.nest.test;

import com.zhaofujun.nest.ddd.AggregateRoot;
import com.zhaofujun.nest.ddd.StringIdentifier;

public class Teacher extends AggregateRoot<StringIdentifier> {
    private int age;
    private User user;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Teacher(StringIdentifier identifier) {
        super(identifier);
    }
}