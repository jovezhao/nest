package com.jovezhao.nest.ddd.builder;

import com.jovezhao.nest.ddd.BaseEntityObject;
import com.jovezhao.nest.ddd.Identifier;

/**
 * 领域实体构建器
 * Created by Jove on 2016/8/30.
 */
public interface IBuilder<T extends BaseEntityObject> {
    T build(Identifier id);

    <U extends T> U build(Class<U> uClass, Identifier id);
}
