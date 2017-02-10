package com.ywkj.nest.ddd;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * Created by Jove on 2017/2/9.
 */
@Target({ElementType.TYPE})
public @interface RepositoryName {
    String value() default "";
}
