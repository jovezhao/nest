package com.jovezhao.nest.test.appservices;

import com.jovezhao.nest.ddd.StringIdentifier;
import com.jovezhao.nest.ddd.builder.EntityLoader;
import com.jovezhao.nest.ddd.builder.RepositoryLoader;
import com.jovezhao.nest.ddd.event.EventBus;
import com.jovezhao.nest.starter.AppService;
import com.jovezhao.nest.test.api.TestDto;
import com.jovezhao.nest.test.api.UserService;
import com.jovezhao.nest.test.models.Teacher;
import com.jovezhao.nest.test.models.User;

/**
 * Created by zhaofujun on 2017/6/16.
 */
@AppService
public class UserServiceImpl implements UserService {
    @Override
    public String changeName(String name) {
        EntityLoader<User> builder = new RepositoryLoader<User>(User.class);
        User user = builder.create(new StringIdentifier("aaa"));
        user.setName(name);

        Teacher teacher = user.act(Teacher.class, new StringIdentifier("tttt"));
        teacher.setClassName("className");

        for (int i = 0; i < 100; i++) {
            TestDto dto = new TestDto();
            dto.setAbs("ffffffff" + i);
            EventBus.publish("event1", dto);

        }
        return user.getName() + user.getId();
    }


//    @Autowired
//    private UserService userService;

}
