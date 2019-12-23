package com.zhaofujun.nest.test;

import com.zhaofujun.nest.context.model.Entity;
import com.zhaofujun.nest.context.model.LongIdentifier;

public abstract class User extends Entity<LongIdentifier> {
    private int userId;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}

