package com.zhaofujun.nest3.application;

import com.zhaofujun.nest3.ddd.StringIdentifier;
import com.zhaofujun.nest3.ddd.Wallet;
import com.zhaofujun.nest3.model.Identifier;
import com.zhaofujun.nest3.model.Repository;

public class WalletRepository implements Repository<Wallet> {

    @Override
    public Class<Wallet> getEntityClass() {
        return Wallet.class;
    }

    @Override
    public Wallet getEntityById(Identifier Identifier) {
        if (Identifier.equals(new StringIdentifier("wallet"))) {
            Wallet wallet = new Wallet((StringIdentifier) Identifier);
            wallet.setBalance(30);
            return wallet;
        }
        return null;
    }

    @Override
    public void insert(Wallet wallet) {
        System.out.println("insert wallet" + wallet.getEndSnapshot());
    }

    @Override
    public void update(Wallet wallet) {
        System.out.println("update wallet" + wallet.getEndSnapshot());

    }

    @Override
    public void delete(Wallet wallet) {

    }
}
