package com.zhaofujun.nest.springboot.test.domain;

import com.zhaofujun.nest.ddd.Entity;
import com.zhaofujun.nest.ddd.LongIdentifier;

public class Teacher extends Entity<LongIdentifier> {
    
    public Teacher(LongIdentifier id) {
        super(id);
    }

    private String name;

    public void init(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
