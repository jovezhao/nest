package com.zhaofujun.nest.core;

public interface EntityLoader<T extends BaseEntity> {
    T create(Identifier id);

    <U extends T> U create(Class<U> uClass, Identifier id);
}
