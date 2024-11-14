package com.zhaofujun.nest.springboot.test.domain;

import com.zhaofujun.nest.ddd.AggregateRoot;
import com.zhaofujun.nest.ddd.LongIdentifier;

public  class User extends AggregateRoot<LongIdentifier> {
    private String name;
    private int age;
    private Address address;
    private Teacher teacher;

    public User(LongIdentifier id) {
        super(id);
    }

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
