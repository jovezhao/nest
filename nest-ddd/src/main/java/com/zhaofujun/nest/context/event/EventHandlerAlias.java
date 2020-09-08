package com.zhaofujun.nest.context.event;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface EventHandlerAlias {
    String value();
}
