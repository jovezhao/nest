package com.zhaofujun.nest.utils.message;

import java.util.Vector;

public class MessageObserver {
    private String name;

    public MessageObserver(String name) {
        this.name = name;
    }
    

    public String getName() {
        return name;
    }


    private Vector<Listener> listeners = new Vector<>();

    public void emit(Object Object) {
        listeners.forEach(p -> p.onReceived(Object));
    }

    public <T> void on(Class<T> tClass, Listener<T> listener) {
        listeners.add(listener);
    }
    public  void on(Listener listener) {
        listeners.add(listener);
    }

    public void remove(Listener listener) {
        listeners.remove(listener);
    }

    public void removeAll() {
        listeners.clear();
    }

}