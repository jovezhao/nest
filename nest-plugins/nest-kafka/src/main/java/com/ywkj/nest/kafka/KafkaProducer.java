package com.ywkj.nest.kafka;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import kafka.common.Topic;
import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;
import kafka.serializer.StringEncoder;

import java.io.Serializable;
import java.util.Properties;

/**
 * Created by Jove on 2016/9/23.
 */
public class KafkaProducer {

    private String zkconnect;
    private String brokers;

    public void setZkconnect(String zkconnect) {
        this.zkconnect = zkconnect;
    }

    public void setBrokers(String brokers) {
        this.brokers = brokers;
    }

    public void publish(String eventName, Serializable object) {
        Producer<String, String> producer = createProducer();

        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        KeyedMessage<String, String> keyedMessage = new KeyedMessage<String, String>(eventName,gson.toJson(object));
        producer.send(keyedMessage);

        producer.close();
    }

    private Producer<String, String> createProducer() {
        Properties props = new Properties();
//        props.put("zk.connect", "192.168.0.244:2181,192.168.0.244:2182,192.168.0.244:2183");
//        props.put("serializer.class", "kafka.serializer.StringEncoder");
//        props.put("metadata.broker.list", "ubuntu:9092");
        props.put("zk.connect", zkconnect);
        props.put("serializer.class", StringEncoder.class.getName());
        props.put("metadata.broker.list", brokers);

        ProducerConfig config = new ProducerConfig(props);
        Producer<String, String> producer = new Producer<String, String>(new ProducerConfig(props));
        return producer;
    }
}
