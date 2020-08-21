package com.zhaofujun.nest.test.domain;

import com.zhaofujun.nest.context.model.BaseEntity;
import com.zhaofujun.nest.context.model.LongIdentifier;


public abstract class User extends BaseEntity<LongIdentifier> {
    private String name;
    private int age;
    private Address address;
    private Teacher teacher;

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public void init(String name, int age, Address address) {
        this.name = name;
        this.age = age;
        this.address = address;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public void changeAge(int age) {
        this.age = age;
    }

    public Address getAddress() {
        return address;
    }

    public Teacher getTeacher() {
        return teacher;
    }
}
