package com.zhaofujun.nest.ddd.context;

import com.alibaba.ttl.TransmittableThreadLocal;

public class QueryContextManager {
    private static TransmittableThreadLocal<QueryContextBox> threadLocal = new TransmittableThreadLocal<>();

    public static QueryContext newServiceContext( ) {
        QueryContext queryContext = new QueryContext();
        QueryContextBox queryContextBox = threadLocal.get();
        if (queryContextBox == null) {
            queryContextBox = new QueryContextBox(queryContext, null);
            threadLocal.set(queryContextBox);
        } else {
            if (queryContextBox.owner()) {
                queryContextBox.push(queryContext);
            } else {
                QueryContextBox newBox = new QueryContextBox(queryContext, queryContextBox);
                threadLocal.set(newBox);
            }
        }
        return queryContext;
    }

    public static QueryContext getCurrentContext() {
        QueryContextBox queryContextBox = threadLocal.get();
        if (queryContextBox == null)
            return null;
        return queryContextBox.get();
    }

    public static void pop() {
        QueryContextBox queryContextBox = threadLocal.get();
        if (queryContextBox == null)
            return;
        if (queryContextBox.owner()) {
            if (queryContextBox.onlyOne()) {
                threadLocal.set(queryContextBox.getParent());
            } else {
                queryContextBox.pop();
            }
        }
    }

}
