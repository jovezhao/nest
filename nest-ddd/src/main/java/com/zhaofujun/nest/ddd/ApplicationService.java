package com.zhaofujun.nest.ddd;

import com.zhaofujun.nest.ddd.context.Transaction;

public interface ApplicationService {
    default Class<? extends Transaction> getTransactionClass() {
        return Transaction.DefaultTransaction.class;
    }
}
