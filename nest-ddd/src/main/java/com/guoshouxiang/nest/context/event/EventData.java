package com.guoshouxiang.nest.context.event;

import com.guoshouxiang.nest.utils.JsonUtils;

import java.io.Serializable;

public abstract class EventData implements Serializable {
   public abstract String getEventCode();
    @Override
    public String toString() {
        return JsonUtils.toJsonString(this);
    }
}
