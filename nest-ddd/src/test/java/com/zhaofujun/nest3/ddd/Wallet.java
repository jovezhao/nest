package com.zhaofujun.nest3.ddd;

import com.zhaofujun.nest3.model.Entity;

public class Wallet extends Entity<StringIdentifier> {


    private int balance;
    private Card card;

    public Wallet(StringIdentifier id) {
        super(id);
    }

    public Wallet() {
    }


    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }
}
