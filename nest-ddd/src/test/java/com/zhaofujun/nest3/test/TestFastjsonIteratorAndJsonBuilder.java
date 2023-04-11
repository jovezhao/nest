package com.zhaofujun.nest3.test;


import com.zhaofujun.nest3.application.context.SnapshotCreator;
import com.zhaofujun.nest3.ddd.Card;
import com.zhaofujun.nest3.ddd.StringIdentifier;
import com.zhaofujun.nest3.ddd.User;
import com.zhaofujun.nest3.ddd.Wallet;
import com.zhaofujun.nest3.impl.fastjson.FastjsonIteratorAndJsonBuilder;
import com.zhaofujun.nest3.impl.fastjson.FastjsonUtils;
import com.zhaofujun.nest3.model.Entity;
import com.zhaofujun.nest3.utils.DefaultEntityPreStore;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class TestFastjsonIteratorAndJsonBuilder {

    private User user;

    @Before
    public void before() {
        user = new User(new StringIdentifier("user"));

        user.setAge(11);
        user.setName("老赵");

        Wallet wallet1 = new Wallet(new StringIdentifier("wallet1"));
        wallet1.setBalance(20);

        ArrayList<Wallet> wallets = new ArrayList<>();
        wallets.add(wallet1);
        user.setWallet(wallets);

        Wallet wallet = new Wallet(new StringIdentifier("wallet"));
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
    public void toJsonString() {
        FastjsonIteratorAndJsonBuilder entityFinder = new FastjsonIteratorAndJsonBuilder();
        DefaultEntityPreStore unitOfWorkStore = new DefaultEntityPreStore();
        user.ready(new SnapshotCreator(entityFinder, entityFinder, unitOfWorkStore));
        unitOfWorkStore.map.forEach(p -> {
            System.out.println(p.getClass() + "快照：" + p.getBeginSnapshot());
        });
    }

    @Test
    public void toEntity() {
        String json = "{\"_version\":0,\"age\":11,\"id\":{\"@type\":\"com.zhaofujun.nest3.ddd.StringIdentifier\",\"id\":\"user\"},\"name\":\"老赵\",\"wallet\":[{\"@type\":\"com.zhaofujun.nest3.ddd.Wallet\",\"id\":{\"@type\":\"com.zhaofujun.nest3.ddd.StringIdentifier\",\"id\":\"wallet1\"},\"__shorthand__\":true}],\"wallet2\":{\"@type\":\"com.zhaofujun.nest3.ddd.Wallet\",\"id\":{\"@type\":\"com.zhaofujun.nest3.ddd.StringIdentifier\",\"id\":\"wallet\"},\"__shorthand__\":true}}\n";
        Entity entity = FastjsonUtils.parseObject(json, User.class);
    }

}

