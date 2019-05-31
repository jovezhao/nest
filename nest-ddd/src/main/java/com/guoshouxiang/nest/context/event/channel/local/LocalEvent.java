package com.guoshouxiang.nest.context.event.channel.local;

import java.util.EventObject;

public class LocalEvent extends EventObject {

    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public LocalEvent(Object source, Object... args) {
        super(source);
        this.args = args;
    }

    private Object[] args;

    public Object[] getArgs() {
        return args;
    }
}
