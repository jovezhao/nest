package com.zhaofujun.nest.context.model;

import com.zhaofujun.nest.utils.identifier.LongIdentifierGenerator;
import com.zhaofujun.nest.utils.identifier.impl.SnowflakeLongIdentifierGenerator;

public class LongIdentifier extends AbstractIdentifier {


    private final Long id;

    public LongIdentifier(Long id) {
        this.id = id;
    }

    public static LongIdentifier valueOf(Long value) {
        return new LongIdentifier(value);
    }

    static LongIdentifierGenerator generator = new SnowflakeLongIdentifierGenerator();
    @Deprecated
    public static LongIdentifier newValue() {
        Long nextValue = generator.nextValue("default");
        return valueOf(nextValue);
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toValue() {
        return id.toString();
    }


}
