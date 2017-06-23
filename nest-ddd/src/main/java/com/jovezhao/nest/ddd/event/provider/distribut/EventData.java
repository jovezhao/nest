package com.jovezhao.nest.ddd.event.provider.distribut;

import com.jovezhao.nest.ddd.Identifier;
import com.jovezhao.nest.ddd.StringIdentifier;
import com.jovezhao.nest.utils.JsonUtils;

import java.io.Serializable;

/**
 * Created by zhaofujun on 2017/6/21.
 */
public class EventData<T> implements Serializable {
    private T data;
    private String dataId;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

}

