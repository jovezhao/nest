package com.zhaofujun.nest.test.application;

import com.zhaofujun.nest.context.appservice.EntityNotifyEventData;
import com.zhaofujun.nest.context.event.DefaultEventBus;
import com.zhaofujun.nest.context.model.EntityFactory;
import com.zhaofujun.nest.context.model.LongIdentifier;
import com.zhaofujun.nest.standard.AppService;
import com.zhaofujun.nest.standard.AppServiceIgnore;
import com.zhaofujun.nest.test.domain.HomeAddress;
import com.zhaofujun.nest.test.domain.Teacher;
import com.zhaofujun.nest.test.domain.User;

@AppService
public class UserApplicationService {

    public void create() throws InterruptedException {
        User user = EntityFactory.create(User.class, LongIdentifier.valueOf(10L));
        user.init("name1", 11, new HomeAddress(1, 2, "address1"));

        Thread.sleep(100);

        Teacher teacher = EntityFactory.create(Teacher.class, LongIdentifier.valueOf(11L));
        teacher.init("teacher 1");

        user.setTeacher(teacher);
//        User user1 = EntityFactory.load(User.class, LongIdentifier.valueOf(10L));
//        user1.delete();

//        change(11);
        EntityNotifyEventData eventData = new EntityNotifyEventData();
        eventData.setServiceName("fdfdfd");
        new DefaultEventBus().publish(eventData,5);
    }

    public void change(int age) {
        User user = EntityFactory.load(User.class, LongIdentifier.valueOf(10L));
        user.changeAge(age);
        user.getTeacher().init("teacher 22");
//        user.delete();
    }

}
