package com.jovezhao.nest.starter;

import org.springframework.stereotype.Service;

import java.lang.annotation.*;

/**
 * Created by zhaofujun on 2017/6/18.
 */
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Service
public @interface AppService {
    String value() default "";
}
