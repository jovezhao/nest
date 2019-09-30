package com.zhaofujun.nest.event;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;
import java.util.function.Consumer;

public class EventListenerManager {
    private List<EventListener> listeners = new ArrayList<>();

    public void addListener(EventListener eventListener) {
        this.listeners.add(eventListener);
    }

    public <T extends EventListener> void publish(Class<T> tClass, Consumer<? super T> doSomething) {
        listeners.stream()
                .filter(p -> tClass.isAssignableFrom(p.getClass()))
                .map(p -> (T) p)
                .forEach(doSomething);
    }


}
