package com.zhaofujun.nest.ioc.test.models;

import com.zhaofujun.nest.context.model.Entity;
import com.zhaofujun.nest.context.model.StringIdentifier;


public class User extends Entity<StringIdentifier> {

    private String pwd;

    public void changPwd(String newpwd) {
//        PasswordChangedEventData eventData = new PasswordChangedEventData(newpwd, this.pwd, this.getId().toValue());

        this.pwd = newpwd;

    }



}
