package com.ywkj.nest.ddd.builder;

import com.ywkj.nest.ddd.EntityObject;

/**
 * 领域实体构建器
 * Created by Jove on 2016/8/30.
 */
public interface IBuilder<T extends EntityObject> {
    T build( String id);
}
