package com.zhaofujun.nest3.ddd;


import com.zhaofujun.nest3.model.AggregateRoot;

import java.util.List;

public class User extends AggregateRoot<StringIdentifier> {


    private String name;
    private int age;
    private List<Wallet> wallet;
    private Wallet wallet2;

    public User(StringIdentifier id) {
        this.id = id;
    }

    public User() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Wallet getWallet2() {
        return wallet2;
    }

    public void setWallet2(Wallet wallet2) {
        this.wallet2 = wallet2;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<Wallet> getWallet() {
        return wallet;
    }

    public void setWallet(List<Wallet> wallet) {
        this.wallet = wallet;
    }
}

