package com.zhaofujun.nest.demo.repositories;

import java.lang.reflect.Type;
import org.springframework.stereotype.Component;

import com.zhaofujun.nest.ddd.Identifier;
import com.zhaofujun.nest.ddd.LongIdentifier;
import com.zhaofujun.nest.ddd.Repository;
import com.zhaofujun.nest.demo.appservices.model.User;

@Component
public class UserRepository implements Repository<User> {

    @Override
    public void insert(User t) {
        System.out.println("新增 User，可以使用 DAO 方式操作数据库");
    }

    @Override
    public void update(User t) {
        System.out.println("修改 User，可以使用 DAO 方式操作数据库");

    }

    @Override
    public void delete(User t) {

    }

    @Override
    public Type getEntityType() {
        return User.class;
    }

    @Override
    public User getEntityById(Class<? extends User> tClass, Identifier identifier) {
        System.out.println("直接创建一个 User 类，模拟数据库查询");

        User user = new User((LongIdentifier) identifier);
        user.setName("test");
        user.setAge(10);
        return user;

    }

}
