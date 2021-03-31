package com.zhaofujun.nest.context.model;


import com.zhaofujun.nest.utils.LongIdentifierUtils;

public class LongIdentifier extends AbstractIdentifier {


    private final Long id;

    public LongIdentifier(Long id) {
        this.id = id;
    }

    public static LongIdentifier valueOf(Long value) {
        return new LongIdentifier(value);
    }

    public static LongIdentifier newValue() {
        return LongIdentifierUtils.createLongIdentifier(LongIdentifierUtils.snowflakeCode, "default");
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toValue() {
        return id.toString();
    }


}
