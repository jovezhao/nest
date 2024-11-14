package com.zhaofujun.nest.ddd.event;

import java.util.Collection;
import com.zhaofujun.nest.ddd.EventHandler;

public interface ChannelConsumer {

    void register(String eventName, Collection<EventHandler> eventHandlers);
}