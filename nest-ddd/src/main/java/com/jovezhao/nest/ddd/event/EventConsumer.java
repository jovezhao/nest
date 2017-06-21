package com.jovezhao.nest.ddd.event;

/**
 * 事件消费者,消费者使用一个独立线程来监听，并且提供停止监听的方法
 *
 * @param <T>
 */
public abstract class EventConsumer<T extends ProviderConfig> implements Runnable {
    private T providerConfig;

    public T getProviderConfig() {
        return providerConfig;
    }


    public void setProviderConfig(T providerConfig) {
        this.providerConfig = providerConfig;
    }


    private EventHandler eventHandler;


    public void setEventHandler(EventHandler eventHandler) {
        this.eventHandler = eventHandler;
    }

    public EventHandler getEventHandler() {
        return eventHandler;
    }

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

    protected abstract void consume();

    protected abstract void dispose();

    private volatile boolean running;

    public void stop() {
        this.running = false;
    }

}
