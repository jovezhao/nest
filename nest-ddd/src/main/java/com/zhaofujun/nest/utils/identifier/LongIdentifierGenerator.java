package com.zhaofujun.nest.utils.identifier;

public interface LongIdentifierGenerator extends IdentifierGenerator<Long> {

    /**
     * @return Get a {@link Long} value
     * @param type
     */
    @Override
    Long nextValue(String type);
}
