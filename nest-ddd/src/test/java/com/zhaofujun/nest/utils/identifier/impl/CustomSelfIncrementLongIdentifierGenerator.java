package com.zhaofujun.nest.utils.identifier.impl;

import com.zhaofujun.nest.utils.identifier.LongIdentifierGenerator;

import java.util.concurrent.atomic.AtomicLong;

public class CustomSelfIncrementLongIdentifierGenerator implements LongIdentifierGenerator {

    private final AtomicLong sequence = new AtomicLong();

    @Override
    public Long nextValue() {
        return sequence.incrementAndGet();
    }
}
