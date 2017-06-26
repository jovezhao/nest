package com.jovezhao.nest.ddd.repository;

import com.jovezhao.nest.ddd.BaseEntityObject;
import com.jovezhao.nest.ddd.event.provider.distribut.DistributedEventInfo;
import com.jovezhao.nest.exception.SystemException;
import com.jovezhao.nest.log.Log;
import com.jovezhao.nest.log.LogAdapter;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 实体上下文
 * Created by Jove on 2016/8/30.
 */
public class NestUnitOfWork implements IUnitOfWork {

    private Log log = new LogAdapter(NestUnitOfWork.class);

    //region 仓储相关的处理方式
    private ThreadLocal<HashMap<BaseEntityObject, OperateEnum>> threadLocalEntity = new ThreadLocal<>();

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


    public void entityCommit() {

        Iterator iter = getEntityMap().entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<BaseEntityObject, OperateEnum> entry = (Map.Entry) iter.next();
            BaseEntityObject entityObject = entry.getKey();
            OperateEnum operate = entry.getValue();

            IRepository r = RepositoryManager.getEntityRepository(entityObject.getClass());
            switch (operate) {
                case save:
                    EntityCacheManager.put(entityObject);
                    r.save(entityObject);
                    break;
                case remove:
                    EntityCacheManager.put(entityObject);
                    r.remove(entityObject);
            }
        }


    }

    //endregion

    //region 事件相关的处理方式
    private ThreadLocal<Queue<DistributedEventInfo>> threadLocalEvent = new ThreadLocal<>();

    private Queue<DistributedEventInfo> getEvenQueue() {
        Queue<DistributedEventInfo> eventQueue = threadLocalEvent.get();
        if (eventQueue == null) {
            eventQueue = new ArrayDeque<>();
            threadLocalEvent.set(eventQueue);
        }
        return eventQueue;
    }

    @Override
    public void addEvent(DistributedEventInfo distributedEventInfo) {
        getEvenQueue().add(distributedEventInfo);
    }



    public void eventCommit() {
        DistributedEventInfo distributedEventInfo = getEvenQueue().poll();

        while (distributedEventInfo != null) {
            distributedEventInfo.commit();
            distributedEventInfo = getEvenQueue().poll();
        }
    }
    //endregion

    @Override
    @Transactional
    public void commit() {
        try {
            entityCommit();
        } catch (Exception ex) {
            //发生异常时，清空待发送的事件
            getEvenQueue().clear();
            throw new SystemException("提交仓储失败", ex);
        } finally {
            getEntityMap().clear();
        }
        try {
            eventCommit();
        }finally {

        }
    }

    @Override
    public void rollback() {
        //清理待提交的实体和待发送的事件信息
        getEntityMap().clear();
        getEvenQueue().clear();

    }
}
