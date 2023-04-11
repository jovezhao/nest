package com.zhaofujun.nest3.application;


import com.zhaofujun.nest3.application.loader.RepositoryEntityLoader;
import com.zhaofujun.nest3.ddd.StringIdentifier;
import com.zhaofujun.nest3.ddd.User;
import com.zhaofujun.nest3.ddd.Wallet;
import com.zhaofujun.nest3.model.EntityLoader;
import com.zhaofujun.nest3.model.Identifier;
import com.zhaofujun.nest3.model.Repository;

public class UserRepository implements Repository<User> {

    @Override
    public Class<User> getEntityClass() {
        return User.class;
    }

    @Override
    public User getEntityById(Identifier Identifier) {
        EntityLoader<Wallet> entityLoader = new RepositoryEntityLoader<>(Wallet.class);

        User user = new User((StringIdentifier) Identifier);
        user.setName("dbname");
        user.setWallet2(entityLoader.create(new StringIdentifier("wallet")));
        return user;
    }

    @Override
    public void insert(User user) {
        System.out.println("insert" + user.getEndSnapshot());
    }

    @Override
    public void update(User user) {
        System.out.println("update" + user.getEndSnapshot());
    }

    @Override
    public void delete(User user) {

    }
}

