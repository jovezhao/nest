package com.zhaofujun.nest.ddd.event;

import java.util.List;
import java.util.stream.Collectors;

import com.zhaofujun.nest.NestConst;
import com.zhaofujun.nest.ddd.LongIdentifier;
import com.zhaofujun.nest.manager.EventManager;
import com.zhaofujun.nest.manager.ProviderManager;
import com.zhaofujun.nest.utils.EntityUtil;

public class EventAppService {
    private EventInfoQuery query;

    public void setQuery(EventInfoQuery query) {
        this.query = query;
    }

    public List<LongIdentifier> getEventInfoList(int commonSize, int failSize, int maxFailTime) {
        return query.getListToBeSent(commonSize, failSize, maxFailTime).stream()
                .map(p -> {
                    EventMessageModel<?> eventInfo = EntityUtil.load(EventMessageModel.class, new LongIdentifier(p));
                    eventInfo.readyPublish();
                    return eventInfo.getId();
                })
                .collect(Collectors.toList());
    }

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
     * 检查是否处理过当前消息
     * 
     * @param processIdentifier 处理记录标识
     * @return
     */
    public boolean isCompleted(ProcessIdentifier processIdentifier) {
        EventProcessRecordModel eventProcessRecord = EntityUtil.load(EventProcessRecordModel.class, processIdentifier);
        if(eventProcessRecord==null)
        {
            eventProcessRecord=new EventProcessRecordModel(processIdentifier);
        }
        return !eventProcessRecord.getProcessState().equals(ProcessState.completed);
    }

    public void complete(ProcessIdentifier processIdentifier) {
        EventProcessRecordModel eventProcessRecord = EntityUtil.load(EventProcessRecordModel.class, processIdentifier);
        eventProcessRecord.process();
    }
}
