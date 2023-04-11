package com.zhaofujun.nest3.model;


public interface EntityLoader<T extends Entity> {
    T create(Identifier id);

    <U extends T> U create(Class<U> uClass, Identifier id);
}
