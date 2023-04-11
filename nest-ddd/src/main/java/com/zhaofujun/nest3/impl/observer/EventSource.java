package com.zhaofujun.nest3.impl.observer;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/**
 * Created by zhaofujun on 2017/6/22.
 */
public class EventSource {

    private static Map<String, EventSource> eventSourceMap = new HashMap<>();

    public static EventSource getEventSource(String messageGroup) {
        EventSource eventSource = eventSourceMap.get(messageGroup);
        if (eventSource == null) {
            eventSource = new EventSource(messageGroup);
            eventSourceMap.put(messageGroup, eventSource);
        }
        return eventSource;
    }

    public static void removeAll() {
        eventSourceMap.clear();
    }

    //监听器容器
    private Vector<LocalMessageReceivedListener> listeners;
    private Object source;

    public EventSource(Object source) {
        this.source = source;
        this.listeners = new Vector<>();
    }

    //给事件源注册监听器
    public void addEventListener(LocalMessageReceivedListener listener) {
        this.listeners.add(listener);
    }

    public void clearAllListener() {
        this.listeners.clear();
    }

    //当事件发生时,通知注册在该事件源上的所有监听器做出相应的反应（调用回调方法）
    public void send(String... args) {
        this.listeners.forEach(p -> p.onReceived(new LocalEvent("source", args)));
    }
}