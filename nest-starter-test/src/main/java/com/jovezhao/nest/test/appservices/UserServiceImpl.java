package com.jovezhao.nest.test.appservices;

import com.jovezhao.nest.ddd.StringIdentifier;
import com.jovezhao.nest.ddd.builder.IBuilder;
import com.jovezhao.nest.ddd.builder.RepositoryLoader;
import com.jovezhao.nest.ddd.event.EventBus;
import com.jovezhao.nest.starter.AppService;
import com.jovezhao.nest.test.api.UserService;
import com.jovezhao.nest.test.models.Teacher;
import com.jovezhao.nest.test.models.User;
import org.springframework.stereotype.Service;

/**
 * Created by zhaofujun on 2017/6/16.
 */
@AppService
public class UserServiceImpl implements UserService {
    @Override
    public String changeName(String name) {
        IBuilder<User> builder = new RepositoryLoader<User>(User.class);
        User user = builder.build(new StringIdentifier("aaa"));
        user.setName(name);

       Teacher teacher= user.act(Teacher.class, new StringIdentifier("tttt"));
        teacher.setClassName("className");
        EventBus.publish("event1", "test");

        return user.getName() + user.getId();
    }


//    @Autowired
//    private UserService userService;

}
