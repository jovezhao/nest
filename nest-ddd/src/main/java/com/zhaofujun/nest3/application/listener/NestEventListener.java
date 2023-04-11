package com.zhaofujun.nest3.application.listener;

import java.util.EventListener;
import java.util.EventObject;

public interface NestEventListener<U extends EventObject> extends EventListener {
    default int order() {
        return 0;
    }

    void handler(U event);
}
