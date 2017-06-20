package com.jovezhao.nest.ddd;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 实体上下文
 * Created by Jove on 2016/8/30.
 */
public  class NestUnitOfWork implements IUnitOfWork {


    private static ThreadLocal<HashMap<BaseEntityObject, OperateEnum>> threadLocal = new ThreadLocal<>();
    private static ThreadLocal<HashMap<String, Object>> threadLocalEvent = new ThreadLocal<>();


    private HashMap<BaseEntityObject, OperateEnum> getmap() {
        HashMap<BaseEntityObject, OperateEnum> hashMap = threadLocal.get();
        if (threadLocal.get() == null) {
            hashMap = new HashMap<>();
            threadLocal.set(hashMap);
        }
        return hashMap;
    }

    public void addEntityObject(BaseEntityObject entityObject) {
        getmap().put(entityObject, OperateEnum.save);
    }

    public void removeEntityObject(BaseEntityObject entityObject) {
        getmap().put(entityObject, OperateEnum.remove);
    }

    @Override
    public void rollback() {

    }

    protected  void beforeCommit(){}

    public void commit() {

        beforeCommit();
        try {
            Iterator iter = getmap().entrySet().iterator();
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
            getmap().clear();
            afterCommit();
        }

    }


    protected  void afterCommit(){}

}
