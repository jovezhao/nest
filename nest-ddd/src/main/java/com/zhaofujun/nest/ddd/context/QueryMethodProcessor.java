package com.zhaofujun.nest.ddd.context;

public class QueryMethodProcessor {
    private MethodInvoker methodInvoker;

    public QueryMethodProcessor(MethodInvoker methodInvoker) {
        this.methodInvoker = methodInvoker;
    }

    public Object doInvoke() throws Throwable {

        QueryContext queryContext = QueryContextManager.newServiceContext();

        Object result = null;

        queryContext.begin();
        try {
            result = methodInvoker.invoke();
            queryContext.submit();
        } finally {
            queryContext.end();
            QueryContextManager.pop();
        }
        return result;
    }
}
