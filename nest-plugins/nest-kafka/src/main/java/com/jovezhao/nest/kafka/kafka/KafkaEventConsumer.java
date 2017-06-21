package com.jovezhao.nest.kafka.kafka;

import com.jovezhao.nest.ddd.event.EventConsumer;
import com.jovezhao.nest.ddd.event.EventData;
import com.jovezhao.nest.ddd.event.EventDataProcessor;
import com.jovezhao.nest.utils.JsonUtils;
import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by zhaofujun on 2017/6/22.
 */
public class KafkaEventConsumer extends EventConsumer<KafkaProviderConfig> {
    private ConsumerConnector createConsumer() {
        Properties properties = new Properties();
//        properties.put("zookeeper.connect", zkconnect);//声明zk
//        properties.put("group.id","spread-event1-group");// 必须要使用别的组名称， 如果生产者和消费者都在同一组，则不能访问同一组内的topic数据


//        properties.put("auto.offset.reset", "smallest"); //必须要加，如果要读旧数据
        properties.put("zookeeper.connect", this.getProviderConfig().getZkconnect());
        properties.put("group.id", this.getEventHandler().getHandlerName());
        properties.put("zookeeper.session.timeout.ms", "400");
        properties.put("zookeeper.sync.time.ms", "200");
        properties.put("auto.entityCommit.interval.ms", "1000");

        return Consumer.createJavaConsumerConnector(new ConsumerConfig(properties));
    }

    ConsumerConnector consumer;

    @Override
    protected void init() {
        consumer = createConsumer();
    }

    @Override
    protected void consume() {
        Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
        topicCountMap.put(this.getEventHandler().getEventName(), 1); // 一次从主题中获取一个数据
        Map<String, List<KafkaStream<byte[], byte[]>>> messageStreams = consumer.createMessageStreams(topicCountMap);

        //for (KafkaStream<byte[], byte[]> stream : messageStreams.get(work.getEventName())) {
        KafkaStream<byte[], byte[]> stream = messageStreams.get(this.getEventHandler().getEventName()).get(0);// 获取每次接收到的这个数据
        ConsumerIterator<byte[], byte[]> iterator = stream.iterator();
        while (iterator.hasNext()) {
            String json = new String(iterator.next().message());
            EventData eventData = JsonUtils.toObj(json, EventData.class);
            EventDataProcessor processor = new EventDataProcessor(eventData, this.getEventHandler());
            processor.process();
        }


    }

    @Override
    protected void dispose() {
        consumer.shutdown();
    }


}
