package com.zhaofujun.nest.context.event;

import com.zhaofujun.nest.utils.JsonUtils;

import java.io.Serializable;

public abstract class EventData implements Serializable {
   public abstract String getEventCode();
    @Override
    public String toString() {
        return JsonUtils.toJsonString(this);
    }
}
