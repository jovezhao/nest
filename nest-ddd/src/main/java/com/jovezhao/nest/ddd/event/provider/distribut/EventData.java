package com.jovezhao.nest.ddd.event.provider.distribut;

import com.jovezhao.nest.ddd.Identifier;
import com.jovezhao.nest.ddd.StringIdentifier;
import com.jovezhao.nest.utils.JsonUtils;

import java.io.Serializable;

/**
 * Created by zhaofujun on 2017/6/21.
 */
public class EventData implements Serializable {
    private String data;
    private String dataId;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

}

