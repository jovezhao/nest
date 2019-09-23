package com.zhaofujun.nest.ioc.annotation;

import com.zhaofujun.nest.context.model.BaseEntity;

import java.lang.annotation.*;

@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface Store {
    Class<? extends BaseEntity> value();
}
