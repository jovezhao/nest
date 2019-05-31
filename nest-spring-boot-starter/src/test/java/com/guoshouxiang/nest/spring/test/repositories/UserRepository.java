package com.guoshouxiang.nest.spring.test.repositories;

import com.guoshouxiang.nest.context.model.Identifier;
import com.guoshouxiang.nest.context.loader.EntityLoader;
import com.guoshouxiang.nest.context.repository.Repository;
import com.guoshouxiang.nest.spring.test.models.User;

public class UserRepository implements Repository<User> {

    @Override
    public Class<User> getEntityClass() {
        return User.class;
    }

    @Override
    public User getEntityById(Identifier identifier, EntityLoader<User> entityLoader) {
        User user = entityLoader.create(identifier);
        return user;
    }

    @Override
    public void save(User user) {

    }

    @Override
    public void remove(User user) {

    }

}
