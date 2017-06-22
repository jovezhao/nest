package com.jovezhao.nest.ddd.event.provider.dft.observer;

import java.util.EventListener;

/**
 * Created by zhaofujun on 2017/6/22.
 */
public abstract class ObserverEventListener implements EventListener {
    //事件发生后的回调方法
    public abstract void onEvent(ObserverEventObject e);
}
