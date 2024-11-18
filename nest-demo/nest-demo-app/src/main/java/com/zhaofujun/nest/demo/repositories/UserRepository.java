package com.zhaofujun.nest.demo.repositories;

import java.lang.reflect.Type;

import org.springframework.stereotype.Component;

import com.zhaofujun.nest.ddd.Entity;
import com.zhaofujun.nest.ddd.Identifier;
import com.zhaofujun.nest.ddd.LongIdentifier;
import com.zhaofujun.nest.ddd.Repository;
import com.zhaofujun.nest.demo.appservices.model.User;

@Component
public class UserRepository implements Repository<User> {

    @Override
    public void insert(User t) {

    }

    @Override
    public void update(User t) {

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

        User user = new User((LongIdentifier) identifier);
        user.setName("test");
        user.setAge(10);
        return user;

    }

}
