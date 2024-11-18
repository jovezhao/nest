package com.zhaofujun.nest.ddd.event;

import java.util.List;

import com.zhaofujun.nest.config.EventSenderConfig;
import com.zhaofujun.nest.ddd.LongIdentifier;
import com.zhaofujun.nest.utils.LockUtil;
import com.zhaofujun.nest.NestConst;

public class EventSenderWorker extends Thread {

    private int commonSize;
    private int failSize;
    private int maxFailTime;
    private int sleepTime;
    private EventAppService appService;

    public EventSenderWorker(EventSenderConfig sendConfig,
            EventAppService appService) {
        super("事件发布工作线程");
        this.commonSize = sendConfig.getCommonSize();
        this.failSize = sendConfig.getFailSize();
        this.maxFailTime = sendConfig.getMaxFailTime();
        this.sleepTime = sendConfig.getSleepTime();
        this.appService = appService;
    }

    @SuppressWarnings("static-access")
    @Override
    public void run() {

        while (!Thread.currentThread().isInterrupted()) {

            // 从仓储获取EventInfo（待发送20条，失败信息10条，失败次数超过5次后将不再处理）
            // 增加处理次数，状态设置为处理中
            // 本过程可能存同步问题，需要使用锁/分布式锁
            List<LongIdentifier> identifiers = LockUtil.lock(NestConst.eventSenderLock, () -> {
                return appService.getEventInfoList(commonSize, failSize, maxFailTime);
            });

            // 通过事件通道发送事件,并更新仓储中的 EventInfo状态为已完成或失败，新增推送时间
            identifiers.forEach(id -> appService.sendEventInfo(id));

            try {
                Thread.currentThread().sleep(sleepTime);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}