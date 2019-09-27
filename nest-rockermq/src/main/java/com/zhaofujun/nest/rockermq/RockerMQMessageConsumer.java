package com.zhaofujun.nest.rockermq;

import com.zhaofujun.nest.container.BeanFinder;
import com.zhaofujun.nest.context.event.EventHandler;
import com.zhaofujun.nest.context.event.channel.distribute.DistributeMessageConsumer;
import com.zhaofujun.nest.context.event.message.MessageInfo;
import com.zhaofujun.nest.utils.JsonUtils;
import com.zhaofujun.nest.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 *
 **/
@Slf4j
public class RockerMQMessageConsumer extends DistributeMessageConsumer {

    private String brokers;

    private String groupName;

    private String nameSpace;

    private boolean vipChannelEnable = false;

    public RockerMQMessageConsumer(BeanFinder beanFinder, String brokers, String groupName, String nameSpace, boolean vipChannelEnable) {
        super(beanFinder);
        this.brokers = brokers;
        this.groupName = groupName;
        this.nameSpace = nameSpace;
        this.vipChannelEnable = vipChannelEnable;
    }


    @Override
    public void subscribe(EventHandler eventHandler) {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(groupName);
        consumer.setNamesrvAddr(brokers);
        //设置消费者端消息拉取策略，表示从哪里开始消费
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        if (!StringUtils.isEmpty(nameSpace)) {
            consumer.setNamespace(nameSpace);
        }
        consumer.setVipChannelEnabled(vipChannelEnable);
        try {

            consumer.subscribe(eventHandler.getEventCode(), "*");
            consumer.registerMessageListener((MessageListenerConcurrently) (list, context) -> {
                try {
                    for (MessageExt messageExt : list) {
                        String topic = messageExt.getTopic();
                        String tag = messageExt.getTags();
                        String msgId = messageExt.getMsgId();
                        String msg = new String(messageExt.getBody());
                        //System.err.println("rockerMQ消费相应msgId:{},topic:{},msgBody:{},tag:{}",msgId,topic,msg,tag);
                        MessageInfo messageInfo = JsonUtils.toObj(msg, MessageInfo.class);
                        onReceivedMessage(messageInfo, eventHandler, context);
                    }
                } catch (Exception e) {
                    return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                }
                //消费成功
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            });

            consumer.start();
        } catch (MQClientException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onFailed(EventHandler eventHandler, Object context, Exception ex) {
        //消息失败处理机制
    }

    @Override
    protected void onEnds(EventHandler eventHandler, Object context) {
        //消息成功处理机制
    }
}
