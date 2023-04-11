package com.zhaofujun.nest3.impl.observer;

import java.util.EventListener;

public interface LocalMessageReceivedListener extends EventListener {
    //事件发生后的回调方法
    void onReceived(LocalEvent e);
}
