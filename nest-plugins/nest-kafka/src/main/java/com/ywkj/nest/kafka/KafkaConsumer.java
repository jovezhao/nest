package com.ywkj.nest.kafka;


import com.ywkj.nest.core.exception.GeneralException;
import com.ywkj.nest.core.log.ILog;
import com.ywkj.nest.core.log.LogAdapter;
import com.ywkj.nest.core.utils.JSONTools;
import com.ywkj.nest.ddd.IEventHandler;
import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * Created by Jove on 2016/9/23.
 */
public class KafkaConsumer implements Runnable {


    public KafkaConsumer(String zkconnect, EventWork work, int prefetchCount) {
        this.zkconnect = zkconnect;
        this.work = work;
        this.prefetchCount = prefetchCount;
    }

    private ILog logger = new LogAdapter(KafkaConsumer.class);

    private String zkconnect;
    private EventWork work;
    private int prefetchCount;
    private volatile boolean status;

    public void stop(){
        this.status=false;
    }


    @Override
    public void run() {
        status=true;
        //Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        ConsumerConnector consumer = createConsumer();
        Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
        topicCountMap.put(work.getEventName(), 1); // 一次从主题中获取一个数据
        while (status) { //循环读取队列中的数据
            Map<String, List<KafkaStream<byte[], byte[]>>> messageStreams = consumer.createMessageStreams(topicCountMap);

//            for (KafkaStream<byte[], byte[]> stream : messageStreams.get(work.getEventName())) {
                KafkaStream<byte[], byte[]> stream = messageStreams.get(work.getEventName()).get(0);// 获取每次接收到的这个数据
                ConsumerIterator<byte[], byte[]> iterator = stream.iterator();
                while (iterator.hasNext()) {
                    String json = new String(iterator.next().message());
                    EventDataDto dto = (EventDataDto) JSONTools.toObj(json, EventDataDto.class);
                    boolean flag = false;
                    try {
                        flag = work.doWork(dto);
                        consumer.commitOffsets();
                    } catch (GeneralException e) {
                        //处理消息失败
                        logger.error(e);
                    } catch (Exception e) {
                        //处理消息失败
                        logger.fatal(e);
                    }
                }

            }
//        }
    }

    private ConsumerConnector createConsumer() {
        Properties properties = new Properties();
//        properties.put("zookeeper.connect", zkconnect);//声明zk
//        properties.put("group.id","spread-event-group");// 必须要使用别的组名称， 如果生产者和消费者都在同一组，则不能访问同一组内的topic数据


//        properties.put("auto.offset.reset", "smallest"); //必须要加，如果要读旧数据
        properties.put("zookeeper.connect", zkconnect);
        properties.put("group.id", work.getHandlerName());
        properties.put("zookeeper.session.timeout.ms", "400");
        properties.put("zookeeper.sync.time.ms", "200");
        properties.put("auto.commit.interval.ms", "1000");

        return Consumer.createJavaConsumerConnector(new ConsumerConfig(properties));
    }


    public static void main(String[] args) {
        EventWork work = new EventWork("spread-event", new IEventHandler() {
            @Override
            public String getEventName() {
                return "spread-event";
            }

            @Override
            public void handle(Object data) {
                System.out.println(data);
            }
        });

        new Thread(new KafkaConsumer("192.168.0.244:2181,192.168.0.244:2182,192.168.0.244:2183",work,10) ,"KafkaConsumer").start();

        try {
            TimeUnit.SECONDS.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
