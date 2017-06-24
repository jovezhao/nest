package com.jovezhao.nest.ddd.event.provider.dft;

import com.jovezhao.nest.ddd.event.EventConsumer;
import com.jovezhao.nest.ddd.event.provider.dft.observer.ObserverEventListener;
import com.jovezhao.nest.ddd.event.provider.dft.observer.ObserverEventObject;
import com.jovezhao.nest.ddd.event.provider.dft.observer.ObserverEventSource;

/**
 * 消费者
 * Created by zhaofujun on 2017/6/22.
 */
public class DefaultEventConsumer extends EventConsumer {
    private ObserverEventSource eventSource;

    public DefaultEventConsumer(ObserverEventSource eventSource) {
        this.eventSource = eventSource;
    }

    @Override
    protected void start() {
        eventSource.addEventListener(new DefaultEventListener(this));
    }


    @Override
    public void stop() {
        eventSource.clearAllListener();
    }

}


