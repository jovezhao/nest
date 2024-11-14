package com.zhaofujun.nest.springboot.test.repositories;

import org.springframework.stereotype.Component;

import com.zhaofujun.nest.ddd.Identifier;
import com.zhaofujun.nest.ddd.Repository;
import com.zhaofujun.nest.springboot.test.domain.User;

@Component
public class UserRepository implements Repository<User> {
    @Override
    public Class<User> getEntityClass() {
        return User.class;
    }

    @Override
    public User getEntityById(Class tClass, Identifier identifier) {
        System.out.println("加载");
        return null;
    }

    @Override
    public void insert(User t) {
        System.out.println("新增");
    }

    @Override
    public void update(User t) {
        System.out.println("修改");
    }

    @Override
    public void delete(User t) {
        System.out.println("删除");
    }

}
