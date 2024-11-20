package com.zhaofujun.nest.test;

import java.lang.reflect.InvocationTargetException;

import com.zhaofujun.nest.NestEngine;
import com.zhaofujun.nest.ddd.ApplicationService;
import com.zhaofujun.nest.ddd.StringIdentifier;
import com.zhaofujun.nest.ddd.event.EventAppService;
import com.zhaofujun.nest.inner.DefaultEventInfoRepository;
import com.zhaofujun.nest.utils.EntityUtil;
import com.zhaofujun.nest.utils.EventUtil;

public class NestTest {

    public static void main(String[] args) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException  {
        NestEngine nestEngine = new NestEngine();

        EventAppService eventAppService = AppServiceUtil.create(EventAppService.class);
        eventAppService.setQuery(new DefaultEventInfoRepository());

        nestEngine.setEventAppService(eventAppService);
        nestEngine.init(null);
        nestEngine.start();

        UserAppService userAppService = AppServiceUtil.create(UserAppService.class);
        userAppService.test();
        userAppService.update();

    }
}


class UserAppService implements ApplicationService {
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
