package com.guoshouxiang.nest.context.event;

public abstract class BaseEvent<T extends EventData> {

    public abstract String getEventCode();

    private T eventObject;
    private String source;

    public T getEventObject() {
        return eventObject;
    }

    public String getSource() {
        return source;
    }

    public BaseEvent(T eventObject, String source) {
        this.eventObject = eventObject;
        this.source = source;
    }
}

