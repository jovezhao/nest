package com.jovezhao.nest.kafka.kafka;

import com.jovezhao.nest.ddd.event.ChannelProvider;
import com.jovezhao.nest.ddd.event.EventConsumer;
import com.jovezhao.nest.ddd.event.EventProducer;

/**
 * 使用Kafka的消息通道
 * Created by zhaofujun on 2017/6/21.
 */
public class KafkaChannelProvider extends ChannelProvider<KafkaProviderConfig> {


    @Override
    protected EventConsumer getEventConsumer() {
        KafkaEventConsumer consumer = new KafkaEventConsumer();
        consumer.setProviderConfig(this.getProviderConfig());
        return consumer;
    }

    @Override
    public EventProducer getEventProducer() {
        KafkaEventProducer producer = new KafkaEventProducer();
        producer.setProviderConfig(this.getProviderConfig());
        return producer;
    }
}
