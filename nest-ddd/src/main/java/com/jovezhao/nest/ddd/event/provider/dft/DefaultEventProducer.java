package com.jovezhao.nest.ddd.event.provider.dft;

import com.jovezhao.nest.ddd.event.EventProducer;
import com.jovezhao.nest.ddd.event.provider.dft.observer.ObserverEventObject;
import com.jovezhao.nest.ddd.event.provider.dft.observer.ObserverEventSource;

import java.io.Serializable;

/**
 * Created by zhaofujun on 2017/6/22.
 */
public class DefaultEventProducer extends EventProducer {

    ObserverEventSource eventSource;

    public DefaultEventProducer(ObserverEventSource eventSource) {
        this.eventSource = eventSource;
    }

    @Override
    protected void sendMessage(String eventName, Serializable object) {
        DefaultEventData eventData = new DefaultEventData(object, eventName);
        ObserverEventObject eventObject = new ObserverEventObject(eventData);
        eventSource.send(eventObject);
    }
}
