package com.zhaofujun.nest.context.appservice;

public class UnitOfWorkCommitor {
    private UnitOfWork unitOfWork;

    public void setUnitOfWork(UnitOfWork unitOfWork) {
        this.unitOfWork = unitOfWork;
    }

    public void commit() {
        this.unitOfWork.commit();
    }
}
