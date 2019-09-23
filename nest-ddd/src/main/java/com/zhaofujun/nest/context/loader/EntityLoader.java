package com.zhaofujun.nest.context.loader;

import com.zhaofujun.nest.context.model.BaseEntity;
import com.zhaofujun.nest.context.model.Identifier;

public interface EntityLoader<T extends BaseEntity> {
    T create(Identifier id);

    <U extends T> U create(Class<U> uClass, Identifier id);
}
