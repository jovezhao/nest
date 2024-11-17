package com.zhaofujun.nest.demo.repositories;

import org.springframework.stereotype.Component;

import com.zhaofujun.nest.ddd.Identifier;
import com.zhaofujun.nest.ddd.LongIdentifier;
import com.zhaofujun.nest.ddd.Repository;
import com.zhaofujun.nest.demo.appservices.model.User;

@Component
public class UserRepository implements Repository<User> {

    @Override
    public Class<User> getEntityClass() {
        return User.class;
    }

    @Override
    public User getEntityById(Class tClass, Identifier identifier) {
        User user = new User((LongIdentifier)identifier);
        user.setName("test");
        user.setAge(10);
        return user;
    }

    @Override
    public void insert(User t) {
        
    }

    @Override
    public void update(User t) {
        
    }

    @Override
    public void delete(User t) {
       
    }

}
