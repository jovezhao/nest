package com.zhaofujun.nest.springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.zhaofujun.nest.ddd.context.Transaction;


public class SpringTransaction implements Transaction {

    @Autowired
    PlatformTransactionManager transcationManager;

    private TransactionStatus status;

    @Override
    public void commit() {
        transcationManager.commit(status);
    }

    @Override
    public void begin() {
        TransactionDefinition def = new DefaultTransactionDefinition();
        status = transcationManager.getTransaction(def);
    }

    @Override
    public void rollback() {
        transcationManager.rollback(status);
    }

}
