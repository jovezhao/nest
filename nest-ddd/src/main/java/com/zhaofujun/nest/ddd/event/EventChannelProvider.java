package com.zhaofujun.nest.ddd.event;

import com.zhaofujun.nest.ddd.ConsumeMode;
import com.zhaofujun.nest.provider.Provider;

/**
 * 
 */
public interface EventChannelProvider extends Provider {

    void commit(String messageGroup, EventData<?> dataObject);

    default ChannelConsumer getConsumer(ConsumeMode mode) {
        if (mode == ConsumeMode.PULL)
            return getPullChannelConsumer();
        else
            return getPushChannelConsumer();
    }

    PullChannelConsumer getPullChannelConsumer();

    PushChannelConsumer getPushChannelConsumer();

}