package com.zhaofujun.nest.test.domain;

import com.zhaofujun.nest.context.model.BaseEntity;
import com.zhaofujun.nest.context.model.LongIdentifier;

public abstract class Teacher extends BaseEntity<LongIdentifier> {
    private String name;

    public void init(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
