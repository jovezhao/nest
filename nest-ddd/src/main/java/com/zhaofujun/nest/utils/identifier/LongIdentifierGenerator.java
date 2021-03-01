package com.zhaofujun.nest.utils.identifier;

public interface LongIdentifierGenerator extends IdentifierGenerator<Long> {


    @Override
    Long nextValue(String type);
}
