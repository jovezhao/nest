package com.zhaofujun.nest.standard;

import java.lang.annotation.*;

/**
 * 忽略应用服务
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface AppServiceIgnore {
}
