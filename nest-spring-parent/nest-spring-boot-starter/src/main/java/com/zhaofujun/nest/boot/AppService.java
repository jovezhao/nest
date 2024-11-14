package com.zhaofujun.nest.boot;

import java.lang.annotation.*;
import com.zhaofujun.nest.ddd.context.Transaction;

/**
 * 标注为应用服务
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface AppService {
    Class transaction() default Transaction.DefaultTransaction.class;
}
