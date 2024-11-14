package com.zhaofujun.nest.ddd.event;

import java.io.Serializable;

public class EventData<T> implements Serializable {
    private String id;
    private T data;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
