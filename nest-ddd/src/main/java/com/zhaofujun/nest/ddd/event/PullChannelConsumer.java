package com.zhaofujun.nest.ddd.event;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import com.zhaofujun.nest.ddd.EventHandler;

public abstract class PullChannelConsumer implements ChannelConsumer {

    List<Thread> workers = new ArrayList<>();

    /**
     * 将每一个eventHandler 创建一个 eventConsumer
     * 为每一个 consumer启动一个子线程，并循环阻塞式拉取消息
     * 获取消息后调用 consumer.invoke()
     * 根据返回信息处理消息确认过程
     * 
     * @param eventName
     * @param eventHandlers
     */
    public void register(String eventName, Collection<EventHandler> eventHandlers) {
        for (EventHandler handler : eventHandlers) {
            MessageProcessor eventConsumer = new MessageProcessor(handler);
            ConsumerWorker consumerWorker = new ConsumerWorker(eventConsumer, this);
            workers.add(consumerWorker);
            consumerWorker.start();
        }
    }

    // 拉取消息并使用 eventConsumer 处理消息,处理成功返回 true
    public abstract boolean pull(MessageProcessor eventConsumer);

    public class ConsumerWorker extends Thread {

        private MessageProcessor eventConsumer;
        private PullChannelConsumer consumer;

        public ConsumerWorker(MessageProcessor eventConsumer, PullChannelConsumer consumer) {
            super("消费者线程启动");
            this.eventConsumer = eventConsumer;
            this.consumer = consumer;
        }

        @Override
        public void run() {
            while (!Thread.currentThread().interrupted()) {
                if (consumer.pull(eventConsumer)) {
                    // 处理成功，休息100ms
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        Thread.interrupted();
                    }
                } else {
                    //处理失败，休息1000ms
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        Thread.interrupted();
                    }
                }

            }
        }
    }
}