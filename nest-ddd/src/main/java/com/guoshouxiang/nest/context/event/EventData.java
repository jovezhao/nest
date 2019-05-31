package com.guoshouxiang.nest.context.event;

import com.guoshouxiang.nest.utils.JsonUtils;

import java.io.Serializable;

public abstract class EventData implements Serializable {
    @Override
    public String toString() {
        return JsonUtils.toJsonString(this);
    }
}
