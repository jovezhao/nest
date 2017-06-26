package com.jovezhao.nest.ddd.builder;

import com.jovezhao.nest.ddd.BaseEntityObject;
import com.jovezhao.nest.ddd.Identifier;

/**
 * 抽象实体加载器
 * Created by Jove on 2016/8/30.
 */
public interface EntityLoader<T extends BaseEntityObject> {
    T create(Identifier id);

    <U extends T> U create(Class<U> uClass, Identifier id);
}
