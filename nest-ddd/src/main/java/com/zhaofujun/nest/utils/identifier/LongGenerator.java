package com.zhaofujun.nest.utils.identifier;

public interface LongGenerator extends IdentifierGenerator<Long> {


    @Override
    Long nextValue(String type);
}
