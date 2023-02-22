package com.zhaofujun.nest.test.application;

import com.zhaofujun.nest.context.appservice.EntityNotifyEventData;
import com.zhaofujun.nest.context.event.DefaultEventBus;
import com.zhaofujun.nest.context.model.EntityFactory;
import com.zhaofujun.nest.context.model.LongIdentifier;
import com.zhaofujun.nest.standard.AppService;
import com.zhaofujun.nest.test.domain.HomeAddress;
import com.zhaofujun.nest.test.domain.Teacher;
import com.zhaofujun.nest.test.domain.User;

import java.io.IOException;

@AppService
public class UserApplicationService {

    public void create() throws InterruptedException {
        User user = EntityFactory.create(User.class, LongIdentifier.valueOf(10L));
        user.init("name1", 11, new HomeAddress(1, 2, "address1"));

        long startTime = System.currentTimeMillis();
        System.out.println("开始执行");
        for (int i = 0; i < 50000; i++) {
            Teacher teacher = EntityFactory.create(Teacher.class, LongIdentifier.valueOf((long) i));
            teacher.init("teacher 1");
            user.setTeacher(teacher);

        }
        long endTime = System.currentTimeMillis();
        System.out.println("执行时间为：" + (endTime - startTime));
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        User user1 = EntityFactory.load(User.class, LongIdentifier.valueOf(10L));
//        user1.delete();

//        change(11);
        EntityNotifyEventData eventData = new EntityNotifyEventData();
        eventData.setServiceName("fdfdfd");
        new DefaultEventBus().publish(eventData, 5);
    }

    public void change(int age) {
        User user = EntityFactory.load(User.class, LongIdentifier.valueOf(10L));
        user.changeAge(age);
        user.getTeacher().init("teacher 22");
//        user.delete();
    }

}
