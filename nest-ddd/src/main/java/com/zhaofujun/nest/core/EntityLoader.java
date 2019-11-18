package com.zhaofujun.nest.core;

import com.zhaofujun.nest.context.model.Entity;

public interface EntityLoader<T extends Entity> {
    T create(Identifier id);

    <U extends T> U create(Class<U> uClass, Identifier id);
}
