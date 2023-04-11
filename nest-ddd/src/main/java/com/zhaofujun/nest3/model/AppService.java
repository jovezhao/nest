package com.zhaofujun.nest3.model;

import java.lang.annotation.*;

/**
 * 标注为应用服务
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface AppService {
}
