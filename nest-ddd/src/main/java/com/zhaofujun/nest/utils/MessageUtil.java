package com.zhaofujun.nest.utils;

import java.util.HashMap;
import java.util.Map;

import com.zhaofujun.nest.utils.message.Listener;
import com.zhaofujun.nest.utils.message.MessageObserver;

public class MessageUtil {
    private static Map<String, MessageObserver> observerMap = new HashMap<>();

    static MessageObserver getMessageObserver(String messageName) {
        MessageObserver observer = observerMap.get(messageName);
        if (observer == null) {
            observer = new MessageObserver(messageName);
            observerMap.put(messageName, observer);
        }
        return observer;
    }
    public static void emit(String messageName,Object object){
        MessageObserver messageObserver = getMessageObserver(messageName);
        messageObserver.emit(object);
    }
    public  static <T> void on(String messageName,Listener<T> listener){
        MessageObserver messageObserver = getMessageObserver(messageName);
        messageObserver.on(listener);
    }
    public  static <T> void on(String messageName,Class<T> tClass, Listener<T> listener){
        MessageObserver messageObserver = getMessageObserver(messageName);
        messageObserver.on(tClass,listener);
    }
}
