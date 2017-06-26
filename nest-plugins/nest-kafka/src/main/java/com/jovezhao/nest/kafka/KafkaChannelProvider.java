package com.jovezhao.nest.kafka;

import com.jovezhao.nest.ddd.event.ChannelProvider;
import com.jovezhao.nest.ddd.event.EventConsumer;
import com.jovezhao.nest.ddd.event.EventProducer;

/**
 * 使用Kafka的消息通道
 * Created by zhaofujun on 2017/6/21.
 */
public class KafkaChannelProvider extends ChannelProvider<KafkaProviderConfig> {


    @Override
    public void init() {

    }

    @Override
    protected EventConsumer createEventConsumer() {
        return new KafkaEventConsumer();
    }

    @Override
    public EventProducer createEventProducer() {
        return new KafkaEventProducer();
    }

}
