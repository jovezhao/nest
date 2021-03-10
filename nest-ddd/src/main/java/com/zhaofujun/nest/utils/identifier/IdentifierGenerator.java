package com.zhaofujun.nest.utils.identifier;

import java.io.Serializable;

public interface IdentifierGenerator<T extends Serializable> {


     String getName();

    T nextValue(String type);
}
