package com.ywkj.nest.rabbitmq;


import java.io.Serializable;

class EventDataDto implements Serializable {
    private Object data;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public EventDataDto(Object data) {
        this.data = data;

    }
}
