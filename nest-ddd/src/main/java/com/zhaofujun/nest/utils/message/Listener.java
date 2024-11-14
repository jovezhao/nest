package com.zhaofujun.nest.utils.message;


public interface Listener<T> {
    void onReceived(T t);
}