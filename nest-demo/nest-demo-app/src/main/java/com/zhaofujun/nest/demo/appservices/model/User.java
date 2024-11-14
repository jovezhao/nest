package com.zhaofujun.nest.demo.appservices.model;

import com.zhaofujun.nest.ddd.AggregateRoot;
import com.zhaofujun.nest.ddd.LongIdentifier;

public class User extends AggregateRoot<LongIdentifier>  {
    private String name;
    private long age;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public long getAge() {
        return age;
    }
    public void setAge(long age) {
        this.age = age;
    }

}
