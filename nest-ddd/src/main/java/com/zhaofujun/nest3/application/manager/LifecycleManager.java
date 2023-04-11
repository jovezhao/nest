package com.zhaofujun.nest3.application.manager;

import com.zhaofujun.nest3.application.NestApplication;
import com.zhaofujun.nest3.application.listener.NestLifecycle;
import com.zhaofujun.nest3.application.listener.NestEventListener;

import java.util.*;

public class LifecycleManager {


    public LifecycleManager(NestApplication nestApplication) {

    }


    private List<NestEventListener> listeners = new ArrayList<>();


    public void addListeners(Collection<NestEventListener> eventListeners) {
        this.listeners.addAll(eventListeners);
    }


    public void addListeners(NestEventListener... eventListeners) {
        this.listeners.addAll(Arrays.asList(eventListeners));
    }

    public void removeListener(NestEventListener eventListener) {
        this.listeners.remove(eventListener);
    }

    public <U extends EventObject> void publish(NestLifecycle nestLifecycle, U eventObject) {
        listeners
                .stream()
                .filter(p -> nestLifecycle.getListenerClass().isInstance(p))
                .sorted(Comparator.comparingInt(p -> p.order()))
                .forEach(p -> p.handler(eventObject));
    }
}
