package com.zhaofujun.nest.standard;

public interface Entity<T extends Identifier> extends DomainObject {
    T getId();

    void verify();

    void delete();
}
