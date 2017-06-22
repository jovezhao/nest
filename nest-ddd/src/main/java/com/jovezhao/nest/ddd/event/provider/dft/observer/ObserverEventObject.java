package com.jovezhao.nest.ddd.event.provider.dft.observer;

import java.util.EventObject;

/**
 * Created by zhaofujun on 2017/6/22.
 */
public class ObserverEventObject extends EventObject {
    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public ObserverEventObject(Object source) {
        super(source);
    }
}
