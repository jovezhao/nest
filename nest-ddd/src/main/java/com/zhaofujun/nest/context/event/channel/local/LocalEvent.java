package com.zhaofujun.nest.context.event.channel.local;

import java.util.EventObject;

public class LocalEvent extends EventObject {


    public LocalEvent(Object source, Object... args) {
        super(source);
        this.args = args;
    }

    private Object[] args;

    public Object[] getArgs() {
        return args;
    }
}
