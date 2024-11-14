package com.zhaofujun.nest.ddd;

public class LongIdentifier extends Identifier {

    private long id;

    public long getId() {
        return id;
    }

    public LongIdentifier(long id) {
        this.id = id;
    }

    @Override
    public String toValue() {
        return String.valueOf(this.id);
    }

}
