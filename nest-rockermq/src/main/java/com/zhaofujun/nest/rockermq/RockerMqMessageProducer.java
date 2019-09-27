package com.zhaofujun.nest.rockermq;

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
public class RockerMqMessageProducer extends DistributeMessageProducer {

    private String brokers;

    private String groupName;

    private String nameSpace;

    private boolean vipChannelEnable = false;

    private DefaultMQProducer producer;

    public RockerMqMessageProducer(String brokers, String groupName, String nameSpace, boolean vipChannelEnable) {
        this.brokers = brokers;
        this.groupName = groupName;
        this.nameSpace = nameSpace;
        this.vipChannelEnable = vipChannelEnable;
    }

    void init() throws MQClientException {
        if (null != producer) {
            return;
        }
        producer = new DefaultMQProducer(groupName);
        producer.setNamesrvAddr(brokers);
        if (!StringUtils.isEmpty(nameSpace)) {
            producer.setNamespace(nameSpace);
        }
        producer.setVipChannelEnabled(vipChannelEnable);
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
}
