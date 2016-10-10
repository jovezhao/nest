package com.ywkj.nest.kafka;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ywkj.nest.ddd.AbstractChannelProvider;
import com.ywkj.nest.ddd.IEventHandler;
import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;

import kafka.producer.ProducerConfig;

import java.util.Properties;

/**
 * Created by Jove on 2016/7/27.
 */
public class KafkaChannelProvider extends AbstractChannelProvider {


    private String zkconnect;
    private String brokers;
    private int prefetchCount;
    private volatile boolean status;


    @Override
    public void publish(String eventName, Object data) {
        KafkaProducer producer = new KafkaProducer();
        producer.setZkconnect(zkconnect);
        producer.setBrokers(brokers);
        EventDataDto dto = new EventDataDto(data);
        producer.publish(eventName, dto);

    }

    KafkaConsumer consumer;

    @Override
    public void subscribe(String eventName, IEventHandler handler) {
        status = true;
        EventWork work = new EventWork(eventName, handler);

        consumer = new KafkaConsumer(eventName, work, prefetchCount);

        Thread workThread = new Thread(consumer);
        workThread.start();

    }

    @Override
    public void stop() {
        if (consumer != null)
            consumer.stop();
    }
}
