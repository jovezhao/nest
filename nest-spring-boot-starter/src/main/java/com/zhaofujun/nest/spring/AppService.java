package com.zhaofujun.nest.spring;

import org.springframework.stereotype.Service;

import java.lang.annotation.*;


@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Service
public @interface AppService {
}
