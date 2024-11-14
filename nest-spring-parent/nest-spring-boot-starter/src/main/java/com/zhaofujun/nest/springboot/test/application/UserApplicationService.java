package com.zhaofujun.nest.springboot.test.application;

import com.zhaofujun.nest.ddd.LongIdentifier;
import com.zhaofujun.nest.springboot.AppService;
import com.zhaofujun.nest.springboot.test.EventDto;
import com.zhaofujun.nest.springboot.test.domain.HomeAddress;
import com.zhaofujun.nest.springboot.test.domain.Teacher;
import com.zhaofujun.nest.springboot.test.domain.User;
import com.zhaofujun.nest.utils.EntityUtil;
import com.zhaofujun.nest.utils.EventUtil;

@AppService
public class UserApplicationService {

    public void create() {
        User user = new User(new LongIdentifier(10L));
        user.init("name1", 11, new HomeAddress(1, 2, "address1"));

        Teacher teacher = new Teacher(new LongIdentifier(11L));
        teacher.init("teacher 1");

        user.setTeacher(teacher);

        EventDto eventDto = new EventDto();
        eventDto.setId("idididi");
        eventDto.setName("nanana");
        EventUtil.publish("Test_AAA", eventDto);

    }

    public void change(int age) {
        User user = EntityUtil.load(User.class, new LongIdentifier(10L));
        user.changeAge(age);
        user.getTeacher().init("teacher 22");
        // user.delete();
    }
}
