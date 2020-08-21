package com.zhaofujun.nest.standard;


public interface EntityLoader<T extends Entity> {
    T create(Identifier id);

    <U extends T> U create(Class uClass, Identifier id);
}
