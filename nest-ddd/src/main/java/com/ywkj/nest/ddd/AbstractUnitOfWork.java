package com.ywkj.nest.ddd;

import com.ywkj.nest.core.cache.CacheClient;
import com.ywkj.nest.core.cache.CacheFactory;
import com.ywkj.nest.core.utils.SpringUtils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 实体上下文
 * Created by Jove on 2016/8/30.
 */
public abstract class AbstractUnitOfWork implements IUnitOfWork {


    private HashMap<EntityObject, OperateEnum> list = new LinkedHashMap<>();

    public void addEntityObject(EntityObject entityObject) {
        list.put(entityObject, OperateEnum.save);
    }

    public void removeEntityObject(EntityObject entityObject) {
        list.put(entityObject, OperateEnum.remove);
    }

    protected abstract void beforeCommit();

    public void commit() {

        beforeCommit();
        try {
            Iterator iter = list.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry<EntityObject, OperateEnum> entry = (Map.Entry) iter.next();
                EntityObject entityObject = entry.getKey();
                OperateEnum operate = entry.getValue();
                //约定各实体仓储的实现在IOC容器中以"实体类名_Repository"为标识
                IRepository r = SpringUtils.getInstance(IRepository.class, entityObject.getClass().getSimpleName() + "_Repository");
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
            list.clear();
        } catch (Exception ex) {
            rollback();
        }
        afterCommit();
    }


    protected abstract void afterCommit();

}
