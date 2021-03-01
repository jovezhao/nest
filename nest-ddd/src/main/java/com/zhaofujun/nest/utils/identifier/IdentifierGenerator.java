package com.zhaofujun.nest.utils.identifier;

import java.io.Serializable;

public interface IdentifierGenerator<T extends Serializable> {

    /**
     * Generator name.
     *
     * @return name
     */
    default String name() {
        return getClass().getName();
    }

    /**
     * Get next ID value.
     *
     * @return next ID
     * @param type
     */
    T nextValue(String type);
}
