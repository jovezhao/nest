package com.jovezhao.nest.test.repositories.models;

import com.jovezhao.nest.ddd.repository.IRepository;
import com.jovezhao.nest.ddd.Identifier;
import com.jovezhao.nest.ddd.builder.EntityLoader;
import com.jovezhao.nest.test.models.User;
import com.jovezhao.nest.test.repositories.mappers.UserDMOMapper;
import com.jovezhao.nest.test.repositories.mappers.dmo.UserDMO;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * Created by zhaofujun on 2017/6/16.
 */
@Repository("User_Repository")
public class UserRepository implements IRepository<User> {
    @Resource
    UserDMOMapper userDMOMapper;

    @Override
    public User getEntityById(Identifier id, EntityLoader<User> builder) {
        User user = builder.create(id);
        UserDMO userDMO = userDMOMapper.selectByPrimaryKey(id.toString());
        user.setName(userDMO.getName());
        return user;
    }

    @Override
    public void save(User user) {
        UserDMO userDMO = new UserDMO();
        userDMO.setId(user.getId().toString());
        userDMO.setName(user.getName());
        if (userDMOMapper.updateByPrimaryKey(userDMO) == 0)
            userDMOMapper.insert(userDMO);

    }

    @Override
    public void remove(User user) {

    }
}
