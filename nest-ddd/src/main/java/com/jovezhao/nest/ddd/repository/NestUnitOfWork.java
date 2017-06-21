package com.jovezhao.nest.ddd.repository;

import com.jovezhao.nest.ddd.BaseEntityObject;
import com.jovezhao.nest.ddd.event.provider.distribut.DistributedChannelProvider;
import com.jovezhao.nest.ddd.event.provider.distribut.EventCommitManager;
import com.jovezhao.nest.ddd.event.provider.distribut.EventData;
import com.jovezhao.nest.ddd.event.provider.distribut.EventSendStatus;

import java.util.*;

/**
 * 实体上下文
 * Created by Jove on 2016/8/30.
 */
public class NestUnitOfWork implements IUnitOfWork {


    private static ThreadLocal<HashMap<BaseEntityObject, OperateEnum>> threadLocalEntity = new ThreadLocal<>();


    private HashMap<BaseEntityObject, OperateEnum> getEntityMap() {
        HashMap<BaseEntityObject, OperateEnum> hashMap = threadLocalEntity.get();
        if (threadLocalEntity.get() == null) {
            hashMap = new HashMap<>();
            threadLocalEntity.set(hashMap);
        }
        return hashMap;
    }


    public void addEntityObject(BaseEntityObject entityObject) {
        getEntityMap().put(entityObject, OperateEnum.save);
    }

    public void removeEntityObject(BaseEntityObject entityObject) {
        getEntityMap().put(entityObject, OperateEnum.remove);
    }


    @Override
    public void rollback() {

    }

    protected void beforeCommit() {
    }

    public void entityCommit() {

        beforeCommit();
        try {
            Iterator iter = getEntityMap().entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry<BaseEntityObject, OperateEnum> entry = (Map.Entry) iter.next();
                BaseEntityObject entityObject = entry.getKey();
                OperateEnum operate = entry.getValue();

                IRepository r = RepositoryManager.getEntityRepository(entityObject.getClass());
                switch (operate) {
                    case save:
                        EntityObjectCacheManager.put(entityObject);
                        r.save(entityObject);
                        break;
                    case remove:
                        EntityObjectCacheManager.put(entityObject);
                        r.remove(entityObject);
                }
            }
        } catch (Exception ex) {

            rollback();
            throw ex;
        } finally {
            getEntityMap().clear();
            afterCommit();
        }

    }


    protected void afterCommit() {
    }


    //region 事件相关的处理方式
    private static ThreadLocal<Queue<EventData>> threadLocalEvent = new ThreadLocal<>();

    private Queue<EventData> getEvenQueue() {
        Queue<EventData> eventQueue = threadLocalEvent.get();
        if (eventQueue == null) {
            eventQueue = new ArrayDeque<>();
            threadLocalEvent.set(eventQueue);
        }
        return eventQueue;
    }

    @Override
    public void addEvent(EventData eventData) {
        getEvenQueue().add(eventData);
    }

    @Override
    public void eventCommit() {
        EventData eventData = getEvenQueue().poll();

        while (eventData != null) {
            eventData.commit();
            eventData = getEvenQueue().poll();
        }
    }
    //endregion
}
