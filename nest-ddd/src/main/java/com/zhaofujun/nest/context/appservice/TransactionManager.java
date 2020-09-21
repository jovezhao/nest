package com.zhaofujun.nest.context.appservice;


import java.util.function.Consumer;

public interface TransactionManager{
     void commit(Runnable runnable);
}