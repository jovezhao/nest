package com.zhaofujun.nest.test;

import com.zhaofujun.nest.context.model.Entity;
import com.zhaofujun.nest.context.model.StringIdentifier;

public class Teacher extends Entity<StringIdentifier> {
    private User user;
    private int tid;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }
}
