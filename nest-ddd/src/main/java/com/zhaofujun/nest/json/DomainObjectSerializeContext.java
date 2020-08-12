package com.zhaofujun.nest.json;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.zhaofujun.nest.core.DomainObject;

import java.util.HashSet;

public class DomainObjectSerializeContext {
    private static TransmittableThreadLocal<HashSet<DomainObject>> threadLocal = new TransmittableThreadLocal();

    private static HashSet<DomainObject> current() {
        HashSet<DomainObject> domainObjects = (HashSet)threadLocal.get();
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
        HashSet<DomainObject> domainObjects = current();
        if (domainObjects.contains(domainObject)) {
            return false;
        } else {
            domainObjects.add(domainObject);
            return true;
        }
    }
}
