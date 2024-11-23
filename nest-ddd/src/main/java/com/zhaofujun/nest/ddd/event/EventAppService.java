package com.zhaofujun.nest.ddd.event;

import java.util.List;
import java.util.stream.Collectors;

import com.zhaofujun.nest.NestConst;
import com.zhaofujun.nest.ddd.ApplicationService;
import com.zhaofujun.nest.ddd.LongIdentifier;
import com.zhaofujun.nest.manager.EventManager;
import com.zhaofujun.nest.manager.ProviderManager;
import com.zhaofujun.nest.utils.EntityUtil;

/**
 * 事件应用服务类，负责事件的发送和处理。
 */
public class EventAppService implements ApplicationService {
    private EventMessageQuery query;

    /**
     * 设置事件信息查询对象。
     *
     * @param query 事件信息查询对象
     */
    public void setQuery(EventMessageQuery query) {
        this.query = query;
    }

    /**
     * 获取事件信息列表。
     *
     * @param commonSize  普通事件大小
     * @param failSize    失败事件大小
     * @param maxFailTime 最大失败次数
     * @return 事件信息列表
     */
    public List<LongIdentifier> getEventInfoList(int commonSize, int failSize, int maxFailTime) {
        return query.getListToBeSent(commonSize, failSize, maxFailTime).stream()
                .map(p -> {
                    EventMessageModel<?> eventInfo = EntityUtil.load(EventMessageModel.class, new LongIdentifier(p));
                    eventInfo.readyPublish();
                    return eventInfo.getId();
                })
                .collect(Collectors.toList());
    }

    /**
     * 发送事件信息。
     *
     * @param identifier 事件标识
     */
    public void sendEventInfo(LongIdentifier identifier) {
        EventMessageModel<?> eventInfo = EntityUtil.load(EventMessageModel.class, identifier);

        // 根据事件名找到通道
        String channelCode = EventManager.getChannelCode(eventInfo.getEventName());
        EventChannelProvider eventChannelProvider = ProviderManager.get(EventChannelProvider.class, channelCode,
                NestConst.defaultChannel);

        EventData<? super Object> eventData = new EventData<>();
        eventData.setData(eventInfo.getEventData());
        eventData.setId(eventInfo.getId().toValue());

        try {
            eventChannelProvider.commit(eventInfo.getEventName(), eventData);
            eventInfo.publish();
        } catch (Exception ex) {
            eventInfo.fail();
        }

    }

    /**
     * 检查是否处理过当前消息。
     *
     * @param processIdentifier 处理记录标识
     * @return true处理过，false 没有处理
     */
    public boolean isCompleted(ProcessIdentifier processIdentifier) {
        EventProcessRecordModel eventProcessRecord = EntityUtil.load(EventProcessRecordModel.class, processIdentifier);
        if (eventProcessRecord == null) {
            eventProcessRecord = new EventProcessRecordModel(processIdentifier);
        }
        return eventProcessRecord.getProcessState().equals(ProcessState.completed);
    }

    /**
     * 完成事件处理。
     *
     * @param processIdentifier 处理记录标识
     */
    public void complete(ProcessIdentifier processIdentifier) {
        EventProcessRecordModel eventProcessRecord = EntityUtil.load(EventProcessRecordModel.class, processIdentifier);
        eventProcessRecord.process();
    }

}
