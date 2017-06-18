package com.jovezhao.nest.test.models;

import com.jovezhao.nest.ddd.EntityObject;

/**
 * Created by zhaofujun on 2017/6/17.
 */
public class User extends EntityObject {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
