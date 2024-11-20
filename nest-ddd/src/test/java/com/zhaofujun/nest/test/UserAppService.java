package com.zhaofujun.nest.test;

import com.zhaofujun.nest.ddd.ApplicationService;
import com.zhaofujun.nest.ddd.StringIdentifier;
import com.zhaofujun.nest.utils.EntityUtil;
import com.zhaofujun.nest.utils.EventUtil;

public  class UserAppService implements ApplicationService {
    public void test() {
        User user = new User(new StringIdentifier("111"));
        user.setName("name");
        Teacher teacher = new Teacher(new StringIdentifier("222"));
        teacher.setAge(10);
        teacher.setUser(user);
    }

    public void update() {
        Teacher teacher = EntityUtil.load(Teacher.class, new StringIdentifier("222"));
        teacher.setAge(333);
        teacher.getUser().setName("new name");
        EventUtil.publish("test", teacher, 0);
        EventUtil.publish("test", "我想试试", 100);

    }
}