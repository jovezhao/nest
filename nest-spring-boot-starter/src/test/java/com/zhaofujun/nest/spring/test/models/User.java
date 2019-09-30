package com.zhaofujun.nest.spring.test.models;

import com.zhaofujun.nest.context.model.BaseEntity;
import com.zhaofujun.nest.context.model.StringIdentifier;


public class User extends BaseEntity<StringIdentifier> {

    private String pwd;

    public void changPwd(String newpwd) {
        PasswordChangedEventData eventData = new PasswordChangedEventData(newpwd, this.pwd, this.getId().toValue());

        this.pwd = newpwd;

    }


}
