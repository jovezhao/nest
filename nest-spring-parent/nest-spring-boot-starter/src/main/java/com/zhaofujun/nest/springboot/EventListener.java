package com.zhaofujun.nest.springboot;

import java.lang.annotation.Target;

import com.zhaofujun.nest.ddd.ConsumeMode;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface EventListener {
    String eventName();

    Class eventDataClass();

    ConsumeMode consumeMode() default ConsumeMode.PUSH;

}
