package com.jovezhao.nest.ddd.event.provider.distribut;

import com.jovezhao.nest.ddd.Identifier;
import java.io.Serializable;

/**
 * Created by zhaofujun on 2017/6/21.
 */
public class EventData implements Serializable {
    private Serializable data;
    private Identifier dataId;

    public Serializable getData() {
        return data;
    }

    public void setData(Serializable data) {
        this.data = data;
    }

    public Identifier getDataId() {
        return dataId;
    }

    public void setDataId(Identifier dataId) {
        this.dataId = dataId;
    }
}
