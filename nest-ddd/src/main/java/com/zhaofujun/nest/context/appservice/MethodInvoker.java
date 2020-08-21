package com.zhaofujun.nest.context.appservice;

public interface MethodInvoker {
    String getMethodName();

    Object invoke() throws Throwable;

    Class getTargetClass();

    Object getTarget();
}
