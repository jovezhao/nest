package com.jovezhao.nest.test.models;

import com.jovezhao.nest.ddd.AbstractRole;

/**
 * Created by zhaofujun on 2017/6/20.
 */
public class Teacher extends AbstractRole<User> {
    private String className;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
