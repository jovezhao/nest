package com.zhaofujun.nest.ddd.context;

import java.lang.reflect.Method;

public interface MethodInvoker {
    String getMethodName();

    Object invoke() throws Throwable;

    Class getTargetClass();

    Object getTarget();

    Method getMethod();
}
