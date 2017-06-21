package com.jovezhao.nest.ddd.event.provider.distribut;

import com.jovezhao.nest.ddd.event.ChannelProvider;
import com.jovezhao.nest.ddd.repository.IUnitOfWork;
import com.jovezhao.nest.utils.SpringUtils;

/**
 * 分布式的消息提供者
 * Created by zhaofujun on 2017/6/21.
 */
public abstract class DistributedChannelProvider implements ChannelProvider {
    @Override
    public void sendMessage(String eventName, Object object) {
        // 发布式的消息提供者需要考虑到消息最终一致性问题
        // 需要使用工作单元来记录待发送的消息
        // 等到工作单元提交完成后再向消息中间件提交消息
        IUnitOfWork unitOfWork = SpringUtils.getInstance(IUnitOfWork.class);
        EventData eventData = EventData.createEvent(eventName, object);
        unitOfWork.addEvent(eventData);
    }


    public abstract void commitMessage(String eventName, Object eventData);
}
