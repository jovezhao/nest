package com.zhaofujun.nest3.application.listener;

import com.zhaofujun.nest3.application.NestApplication;

import java.util.EventObject;

public class NestApplicationEventObject extends EventObject {
    private NestApplication nestApplication;

    public NestApplicationEventObject(Object source, NestApplication nestApplication) {
        super(source);
        this.nestApplication = nestApplication;
    }

    public NestApplication getNestApplication() {
        return nestApplication;
    }
}
