package com.zhaofujun.nest.rocketmq;

import com.zhaofujun.nest.container.BeanFinder;
import com.zhaofujun.nest.context.event.EventHandler;
import com.zhaofujun.nest.context.event.channel.distribute.DistributeMessageConsumer;
import com.zhaofujun.nest.context.event.message.MessageInfo;
import com.zhaofujun.nest.utils.JsonUtils;
import com.zhaofujun.nest.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;

/**
 *
 **/
@Slf4j
public class RocketMQMessageConsumer extends DistributeMessageConsumer {

    private RocketMqProperties rockerMqProperties;

    public RocketMQMessageConsumer(BeanFinder beanFinder,RocketMqProperties rockerMqProperties) {
        super(beanFinder);
        this.rockerMqProperties=rockerMqProperties;
    }


    @Override
    public void subscribe(EventHandler eventHandler) {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(this.rockerMqProperties.getGroupName());
        consumer.setNamesrvAddr((this.rockerMqProperties.getBrokers()));
        //设置消费者端消息拉取策略，表示从哪里开始消费
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        if (!StringUtils.isEmpty((this.rockerMqProperties.getNameSpace()))) {
            consumer.setNamespace((this.rockerMqProperties.getNameSpace()));
        }
        consumer.setVipChannelEnabled((this.rockerMqProperties.getVipChannelEnable()));
        consumer.setConsumeMessageBatchMaxSize(this.rockerMqProperties.getConsumeMessageBatchMaxSize());
        try {

            consumer.subscribe(eventHandler.getEventCode(), "*");
            consumer.registerMessageListener((MessageListenerConcurrently) (list, context) -> {
                try {
                    for (MessageExt messageExt : list) {
                        String topic = messageExt.getTopic();
                        String tag = messageExt.getTags();
                        String msgId = messageExt.getMsgId();
                        String msg = new String(messageExt.getBody());
                        log.debug("rockerMQ消费相应msgId:{},topic:{},msgBody:{},tag:{}",msgId,topic,msg,tag);
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
