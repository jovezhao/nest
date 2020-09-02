package com.zhaofujun.nest.utils.identifier;

public interface LongIdentifierGenerator extends IdentifierGenerator<Long> {

    /**
     * @return Get a {@link Long} value
     */
    @Override
    Long nextValue();
}
