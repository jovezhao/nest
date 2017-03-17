package com.ywkj.nest.ddd;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 实体上下文
 * Created by Jove on 2016/8/30.
 */
public abstract class AbstractUnitOfWork implements IUnitOfWork {


    private static ThreadLocal<HashMap<EntityObject, OperateEnum>> threadLocal = new ThreadLocal<>();


    private HashMap<EntityObject, OperateEnum> getmap(){
        HashMap<EntityObject, OperateEnum> hashMap=threadLocal.get();
        if(threadLocal.get()==null){
            hashMap=new HashMap<>();
            threadLocal.set(hashMap);
        }
        return hashMap;
    }

    public void addEntityObject(EntityObject entityObject) {
        getmap().put(entityObject, OperateEnum.save);
    }

    public void removeEntityObject(EntityObject entityObject) {
        getmap().put(entityObject, OperateEnum.remove);
    }

    protected abstract void beforeCommit();

    public void commit() {

        beforeCommit();
        try {
            Iterator iter = getmap().entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry<EntityObject, OperateEnum> entry = (Map.Entry) iter.next();
                EntityObject entityObject = entry.getKey();
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
            getmap().clear();
        } catch (Exception ex) {

            rollback();
            throw ex;
        }finally {
            afterCommit();
        }

    }


    protected abstract void afterCommit();

}
