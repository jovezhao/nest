package com.zhaofujun.nest3.ddd;

import com.zhaofujun.nest3.model.Entity;

public class Card extends Entity<StringIdentifier> {

    private String name;

    public Card(StringIdentifier id) {
        super(id);
    }

    public Card() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
