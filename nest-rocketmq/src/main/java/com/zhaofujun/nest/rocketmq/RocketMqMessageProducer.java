package com.zhaofujun.nest.rocketmq;

import com.zhaofujun.nest.context.event.channel.distribute.DistributeMessageProducer;
import com.zhaofujun.nest.context.event.message.MessageInfo;
import com.zhaofujun.nest.utils.JsonUtils;
import com.zhaofujun.nest.utils.StringUtils;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.MixAll;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.io.UnsupportedEncodingException;

/**
 *
 **/
public class RocketMqMessageProducer extends DistributeMessageProducer {

    private RocketMqProperties rockerMqProperties;

    private DefaultMQProducer producer;

    public RocketMqMessageProducer(RocketMqProperties rockerMqProperties) {
        this.rockerMqProperties=rockerMqProperties;
    }

    void init() throws MQClientException {
        if (null != producer) {
            return;
        }
        producer = new DefaultMQProducer(this.rockerMqProperties.getGroupName());
        producer.setNamesrvAddr(this.rockerMqProperties.getBrokers());
        if (!StringUtils.isEmpty(this.rockerMqProperties.getNameSpace())) {
            producer.setNamespace(this.rockerMqProperties.getNameSpace());
        }
        producer.setVipChannelEnabled(this.rockerMqProperties.getVipChannelEnable());
        producer.setCreateTopicKey(MixAll.AUTO_CREATE_TOPIC_KEY_TOPIC);
        producer.start();
    }


    @Override
    public void commit(String messageGroup, MessageInfo messageInfo) {

        try {
            this.init();
            String body = JsonUtils.toJsonString(messageInfo);
            Message message = new Message(messageGroup, body.getBytes(RemotingHelper.DEFAULT_CHARSET));
            producer.send(message);
        } catch (MQClientException | RemotingException | MQBrokerException | InterruptedException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void destruction() {
        if(null !=producer){
            producer.shutdown();
            producer=null;
        }
    }
}
