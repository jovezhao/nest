package com.jovezhao.nest.kafka;

import com.jovezhao.nest.ddd.event.provider.distribut.DistributedEventProducer;
import com.jovezhao.nest.ddd.event.provider.distribut.MessageData;
import com.jovezhao.nest.utils.JsonUtils;
import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;
import kafka.serializer.StringEncoder;

import java.util.Properties;

/**
 * Created by zhaofujun on 2017/6/22.
 */
public class KafkaEventProducer extends DistributedEventProducer<KafkaProviderConfig> {

    @Override
    public void commitMessage(String eventName, MessageData messageData) {
        Properties props = new Properties();

        props.put("zk.connect", this.getProviderConfig().getZk());
        props.put("serializer.class", StringEncoder.class.getName());
        props.put("metadata.broker.list", this.getProviderConfig().getBrokers());

        Producer<String, String> producer = new Producer<String, String>(new ProducerConfig(props));
        KeyedMessage<String, String> keyedMessage = new KeyedMessage<String, String>(eventName, JsonUtils.toJsonString(messageData));
        producer.send(keyedMessage);

        producer.close();
    }
}
