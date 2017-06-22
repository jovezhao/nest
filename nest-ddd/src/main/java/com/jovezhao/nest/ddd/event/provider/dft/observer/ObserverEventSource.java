package com.jovezhao.nest.ddd.event.provider.dft.observer;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by zhaofujun on 2017/6/22.
 */
public class ObserverEventSource {
    //监听器容器
    private Set<ObserverEventListener> listeners;

    public ObserverEventSource() {
        this.listeners = new HashSet<ObserverEventListener>();
    }

    //给事件源注册监听器
    public void addEventListener(ObserverEventListener listener) {
        this.listeners.add(listener);
    }
    public void clearAllListener(){
        this.listeners.clear();
    }

    //当事件发生时,通知注册在该事件源上的所有监听器做出相应的反应（调用回调方法）
    public void send(ObserverEventObject object) {
        ObserverEventListener listener = null;
        Iterator<ObserverEventListener> iterator = this.listeners.iterator();
        while (iterator.hasNext()) {
            listener = iterator.next();
            listener.onEvent(object);
        }
    }
}