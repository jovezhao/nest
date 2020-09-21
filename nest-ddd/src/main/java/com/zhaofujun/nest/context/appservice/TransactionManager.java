package com.zhaofujun.nest.context.appservice;

public interface TransactionManager{
     void commit(Runnable runnable);
}