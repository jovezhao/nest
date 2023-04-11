package com.zhaofujun.nest3.test;

import com.zhaofujun.nest3.application.AggregateRootFactory;
import com.zhaofujun.nest3.application.context.EntityDispatcherImpl;
import com.zhaofujun.nest3.ddd.*;
import com.zhaofujun.nest3.model.Entity;
import com.zhaofujun.nest3.model.Repository;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class TestEntityUnitOfWork {
    private User user;

    @Before
    public void before() {
        user = AggregateRootFactory.create(User.class,new StringIdentifier("user"));

        user.setAge(11);
        user.setName("老赵");
        Wallet wallet1 = new Wallet();
        wallet1.setBalance(20);

        ArrayList<Wallet> wallets = new ArrayList<>();
        wallets.add(wallet1);
        user.setWallet(wallets);

        Wallet wallet = new Wallet();

        wallet.setBalance(22);
        user.setWallet2(wallet);

        Card card1 = new Card(new StringIdentifier("card1"));

        card1.setName("信用卡");
        wallet1.setCard(card1);

        Card card = new Card(new StringIdentifier("card"));
        card.setName("现金");
        wallet.setCard(card);
    }

    @Test
    public void addEntity(){

    }
}
