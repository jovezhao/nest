package com.jovezhao.nest.ddd.event.provider.distribut;

import com.jovezhao.nest.ddd.event.EventConsumer;
import com.jovezhao.nest.ddd.event.ProviderConfig;
import com.jovezhao.nest.exception.SystemException;
import com.jovezhao.nest.log.Log;
import com.jovezhao.nest.log.LogAdapter;

/**
 * 分布式消费者抽象类
 * 需要具体的消息中间件来实现
 * Created by zhaofujun on 2017/6/22.
 */
public abstract class DistributedEventConsumer<T extends ProviderConfig> extends EventConsumer<T> implements Runnable {

    private Log log = new LogAdapter(this.getClass());

    @Override
    public void run() {


        running = true;
        while (running) {
            // 开始消费
            try {
                MessageProcessor dataProcessor = new MessageProcessor(this.getEventHandler());
                consume(dataProcessor);
            } catch (Exception e) {
                e.printStackTrace();
            }
            // 结束消费后开始执行下一次的
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                log.warn(e);
            }

        }


    }


    protected abstract void consume(MessageProcessor processor) throws Exception;


    private volatile boolean running;

    @Override
    public void stop() {
        this.running = false;
    }

    @Override
    protected void start() {
        Thread eventThread = new Thread(this);
        eventThread.start();
    }
}
