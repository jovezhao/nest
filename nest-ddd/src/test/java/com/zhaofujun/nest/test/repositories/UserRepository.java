package com.zhaofujun.nest.test.repositories;

import com.zhaofujun.nest.standard.EntityLoader;
import com.zhaofujun.nest.standard.Identifier;
import com.zhaofujun.nest.standard.Repository;
import com.zhaofujun.nest.test.domain.User;

public class UserRepository implements Repository<User> {
    @Override
    public Class<User> getEntityClass() {
        return User.class;
    }

    @Override
    public User getEntityById(Identifier abstractIdentifier, EntityLoader<User> entityLoader) {
        return null;
    }

    @Override
    public void insert(User user) {

    }

    @Override
    public void update(User user) {

    }

    @Override
    public void delete(User user) {

    }
}
