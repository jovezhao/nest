package com.zhaofujun.nest.json;

import com.zhaofujun.nest.standard.DomainObject;
import com.zhaofujun.nest.standard.Entity;

import java.util.HashSet;

public class DomainObjectSerializeContext {
    private static ThreadLocal<HashSet<DomainObject>> threadLocal = new ThreadLocal();
    private static ThreadLocal<DomainObject> domainObjectThreadLocal = new ThreadLocal<>();

    private static HashSet<DomainObject> current() {
        HashSet<DomainObject> domainObjects = (HashSet) threadLocal.get();
        if (domainObjects == null) {
            domainObjects = new HashSet();
            threadLocal.set(domainObjects);
        }
        return domainObjects;
    }

    public static void clear() {
        HashSet<DomainObject> domainObjects = current();
        domainObjects.clear();
    }

    public static boolean put(DomainObject domainObject) {
        if(!(domainObject instanceof Entity)) return true;
        HashSet<DomainObject> domainObjects = current();
        if (domainObjects.contains(domainObject)) {
            return false;
        } else {
            domainObjects.add(domainObject);
            return true;
        }
    }

    public static void setCurrent(DomainObject domainObject) {
        domainObjectThreadLocal.set(domainObject);
    }

    public static DomainObject getDomainObject() {
        return domainObjectThreadLocal.get();
    }

    public static void clearDomainObject() {
        domainObjectThreadLocal.remove();
    }
}
