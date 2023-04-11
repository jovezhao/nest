package com.zhaofujun.nest3.impl.observer;

import java.util.EventObject;

public class LocalEvent extends EventObject {


    public LocalEvent(String source, String... args) {
        super(source);
        this.args = args;
    }

    private String[] args;

    public String[] getArgs() {
        return args;
    }
}
