package com.jovezhao.nest.ddd.event.provider.distribut;

import com.jovezhao.nest.ddd.Identifier;
import com.jovezhao.nest.ddd.event.EventConsumer;
import com.jovezhao.nest.ddd.event.ProviderConfig;

import java.io.Serializable;

/**
 * Created by zhaofujun on 2017/6/22.
 */
public abstract class DistributedEventConsumer<T extends ProviderConfig> extends EventConsumer<T> implements Runnable {


    @Override
    public void run() {

        try {
            init();
        } catch (Exception e) {
            e.printStackTrace();
        }

        running = true;
        while (running) {
            // 开始消费
            try {
                consume();
            } catch (Exception e) {
                e.printStackTrace();
            }
            // 结束消费后开始执行下一次的
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


        try {
            dispose();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected abstract void init() throws Exception;

    protected abstract void consume() throws Exception;

    protected abstract void dispose() throws Exception;

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
