package com.zhaofujun.nest.event;

import java.util.*;
import java.util.function.Consumer;

public class EventListenerManager {
    private List<NestEventListener> listeners = new ArrayList<>();


    public void addListeners(Collection<NestEventListener> eventListeners) {
        this.listeners.addAll(eventListeners);
    }


    public void addListener(NestEventListener eventListener) {
        this.listeners.add(eventListener);
    }

    public <T extends NestEventListener> void publish(Class<T> tClass, Consumer<? super T> doSomething) {
        listeners.stream()
                .filter(p -> tClass.isAssignableFrom(p.getClass()))
                .map(p -> (T) p)
                .forEach(doSomething);
    }


}
