package com.zhaofujun.nest.ioc.test.repositories;

import com.zhaofujun.nest.ioc.test.models.User;
import com.zhaofujun.nest.context.model.Identifier;
import com.zhaofujun.nest.context.loader.EntityLoader;
import com.zhaofujun.nest.context.repository.Repository;

//@Store(User.class)
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
