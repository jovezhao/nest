package com.zhaofujun.nest3.application.context;

public interface TransactionManager {
     void commit(Runnable runnable)  ;
}