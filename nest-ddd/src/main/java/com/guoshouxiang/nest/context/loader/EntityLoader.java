package com.guoshouxiang.nest.context.loader;

import com.guoshouxiang.nest.context.model.BaseEntity;
import com.guoshouxiang.nest.context.model.Identifier;

public interface EntityLoader<T extends BaseEntity> {
    T create(Identifier id);

    <U extends T> U create(Class<U> uClass, Identifier id);
}
