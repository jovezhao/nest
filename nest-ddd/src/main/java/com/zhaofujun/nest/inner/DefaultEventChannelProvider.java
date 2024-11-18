package com.zhaofujun.nest.inner;

import com.zhaofujun.nest.NestConst;
import com.zhaofujun.nest.ddd.event.EventChannelProvider;
import com.zhaofujun.nest.ddd.event.EventData;
import com.zhaofujun.nest.ddd.event.MessageAck;
import com.zhaofujun.nest.ddd.event.MessageProcessor;
import com.zhaofujun.nest.ddd.event.PullChannelConsumer;
import com.zhaofujun.nest.ddd.event.PushChannelConsumer;
import com.zhaofujun.nest.utils.JsonUtil;
import com.zhaofujun.nest.utils.MessageUtil;

/**
 * 使用 MessageUtil 模拟事件发布过程，请勿在生产环境中使用
 * 在生产环境中可以自定义并使用相同的code覆盖DefaultEventChannelProvider
 */
public class DefaultEventChannelProvider implements EventChannelProvider {

    @Override
    public String getCode() {
        return NestConst.defaultChannel;
    }

    @Override
    public void commit(String messageGroup, EventData<?> eventData) {
        System.out.println("消息发布");
        String messageInfoString = JsonUtil.toJsonString(eventData);
        MessageUtil.emit(messageGroup, messageInfoString);
    }

    @Override
    public PullChannelConsumer getPullChannelConsumer() {
        return new InnerPullConsumer();
    }

    @Override
    public PushChannelConsumer getPushChannelConsumer() {
        return new InnerPushConsumer();
    }

    public class InnerPullConsumer extends PullChannelConsumer {

        @Override
        public boolean pull(MessageProcessor<?> eventConsumer) {
            throw new UnsupportedOperationException("Unimplemented method 'pull'");
        }
    }

    public class InnerPushConsumer extends PushChannelConsumer {

        @Override
        protected <T> void subscribe(MessageProcessor<T> consumer) {
            MessageUtil.on(consumer.getEventName(), (String messageString) -> {
                // 获得事件字符串，进行反序列化
                // 调用 consumer.invoke 方法 处理业务
                EventData<T> dataObject = consumer.toObject(messageString);
                MessageAck ack = consumer.invoke(dataObject);
                // 根据返回的 ack 进行信息确认
            });
        }
    }

}
