package com.zhaofujun.nest3.application;

import com.zhaofujun.nest3.ddd.StringIdentifier;
import com.zhaofujun.nest3.ddd.User;
import com.zhaofujun.nest3.ddd.Wallet;
import com.zhaofujun.nest3.model.AppService;

@AppService
public class UserAppService {
    public void create() {
        User user = AggregateRootFactory.create(User.class, new StringIdentifier("1111"));
        Wallet wallet = new Wallet(new StringIdentifier("wallet"));
        wallet.setBalance(200);
        user.setWallet2(wallet);
        user.setName("testName");

    }

    public void update() {
        User user = AggregateRootFactory.load(User.class, new StringIdentifier("1111"));

        user.getWallet2().setBalance(55);

    }
}
