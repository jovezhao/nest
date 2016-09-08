package com.ywkj.nest.kafka;

import com.ywkj.nest.ddd.AbstractChannelProvider;
import com.ywkj.nest.ddd.IEventHandler;
import kafka.producer.Producer;
import kafka.producer.ProducerConfig;

import java.util.Properties;

/**
 * Created by Jove on 2016/7/27.
 */
public class KafkaChannelProvider extends AbstractChannelProvider {
    public KafkaChannelProvider(Properties properties) {
        this.properties = properties;
    }

    @Override
    public void publish(String eventName, Object data) {
        Producer producer = new Producer(new ProducerConfig(properties));
//        producer.send();
    }

    @Override
    public void subscribe(String eventName, IEventHandler handler) {

    }

    @Override
    public void stop() {

    }
}
