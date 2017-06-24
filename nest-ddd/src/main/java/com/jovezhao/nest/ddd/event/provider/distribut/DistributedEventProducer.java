package com.jovezhao.nest.ddd.event.provider.distribut;

import com.jovezhao.nest.ddd.event.EventProducer;
import com.jovezhao.nest.ddd.event.ProviderConfig;
import com.jovezhao.nest.ddd.repository.IUnitOfWork;
import com.jovezhao.nest.utils.SpringUtils;

import java.io.Serializable;

/**
 * 分布式生产者
 * Created by zhaofujun on 2017/6/21.
 */
public abstract class DistributedEventProducer<T extends ProviderConfig> extends EventProducer<T> {

    @Override
    protected void sendMessage(String eventName, Serializable object)  {
        // 发布式的消息提供者需要考虑到消息最终一致性问题
        // 需要使用工作单元来记录待发送的消息
        // 等到工作单元提交完成后再向消息中间件提交消息
        IUnitOfWork unitOfWork = SpringUtils.getInstance(IUnitOfWork.class);
        DistributedEventInfo distributedEventInfo = DistributedEventInfo.createEventInfo(eventName, object);
        unitOfWork.addEvent(distributedEventInfo);
    }

    protected abstract void commitMessage(String eventName, MessageData messageData);

}
