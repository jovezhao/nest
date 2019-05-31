package com.guoshouxiang.nest.spring.test.models;

import com.guoshouxiang.nest.context.model.BaseEntity;
import com.guoshouxiang.nest.context.model.StringIdentifier;


public class User extends BaseEntity<StringIdentifier> {

    private String pwd;

    public void changPwd(String newpwd) {
        PasswordChangedEventData eventData = new PasswordChangedEventData(newpwd, this.pwd, this.getId().toValue());

        this.pwd = newpwd;

    }


}
