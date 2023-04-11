package com.zhaofujun.nest3.model;

import com.zhaofujun.nest3.application.NestApplication;
import com.zhaofujun.nest3.application.context.ServiceContext;

public abstract class AggregateRoot<T extends Identifier> extends Entity<T> {


    private transient boolean __deleted = false;
    private transient boolean __new = false;


    public boolean is__deleted() {
        return __deleted;
    }

    public boolean is__new() {
        return __new;
    }

    public void delete() {
        this.__deleted = true;
    }

    public void attach() {
        ServiceContext serviceContext = NestApplication.currentServiceContext();
        if (serviceContext != null)
            serviceContext.addAggregateRoot(this);
    }
}
