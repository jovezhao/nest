package com.zhaofujun.nest.ioc.test.repositories;

import com.zhaofujun.nest.ioc.annotation.Store;
import com.zhaofujun.nest.ioc.test.models.User;
import com.zhaofujun.nest.core.Identifier;
import com.zhaofujun.nest.core.EntityLoader;
import com.zhaofujun.nest.core.Repository;

@Store
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
