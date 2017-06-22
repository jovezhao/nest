package com.jovezhao.nest.ddd.event.provider.dft;

import com.jovezhao.nest.ddd.event.EventConsumer;
import com.jovezhao.nest.ddd.event.EventHandler;
import com.jovezhao.nest.ddd.event.provider.dft.observer.ObserverEventListener;
import com.jovezhao.nest.ddd.event.provider.dft.observer.ObserverEventObject;

/**
 * Created by zhaofujun on 2017/6/22.
 */
public class DefaultEventListener extends ObserverEventListener {
    private EventConsumer eventConsumer;

    public DefaultEventListener(EventConsumer eventConsumer) {
        this.eventConsumer = eventConsumer;
    }

    @Override
    public void onEvent(ObserverEventObject e) {
        DefaultEventData eventData = (DefaultEventData) e.getSource();
        if (eventData.getEventName().equals(this.eventConsumer.getEventHandler().getEventName())) {
            //需要处理的事件
            try {
                this.eventConsumer.getEventHandler().handle(eventData.getEventData());
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }
}
