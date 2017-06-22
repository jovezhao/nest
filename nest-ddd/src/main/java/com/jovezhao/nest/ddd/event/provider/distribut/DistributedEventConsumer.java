package com.jovezhao.nest.ddd.event.provider.distribut;

import com.jovezhao.nest.ddd.event.EventConsumer;
import com.jovezhao.nest.ddd.event.ProviderConfig;

/**
 * Created by zhaofujun on 2017/6/22.
 */
public abstract class DistributedEventConsumer<T extends ProviderConfig> extends EventConsumer<T> implements Runnable {


    @Override
    public void run() {
        init();

        running = true;
        while (running) {
            // 开始消费
            consume();
            // 结束消费后开始执行下一次的
        }
        dispose();
    }

    protected abstract void init();


    protected abstract void dispose();

    private volatile boolean running;

    @Override
    public void stop() {
        this.running = false;
    }

    @Override
    protected void start() {
        Thread eventThread = new Thread(this);
        eventThread.setName(this.getEventHandler().getEventName());
        eventThread.start();
    }
}
