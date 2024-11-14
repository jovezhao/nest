package com.zhaofujun.nest.ddd;

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
