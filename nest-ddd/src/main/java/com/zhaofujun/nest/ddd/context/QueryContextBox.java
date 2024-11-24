package com.zhaofujun.nest.ddd.context;

import java.util.Stack;

public class QueryContextBox {
private long threadId;
    private Stack<QueryContext> stack;
    private QueryContextBox parent;

    public QueryContextBox(QueryContext queryContext, QueryContextBox parent) {
        this.threadId = Thread.currentThread().getId();
        this.stack = new Stack<>();
        this.stack.push(queryContext);
        this.parent = parent;
    }

    public boolean onlyOne() {
        return stack.size() == 1;
    }

    public QueryContext get() {
        return stack.peek();
    }

    public QueryContext pop() {
        return stack.pop();
    }

    public QueryContext push(QueryContext queryContext) {
        return stack.push(queryContext);
    }

    public long getThreadId() {
        return threadId;
    }

    public boolean owner(){
       return this.threadId==Thread.currentThread().getId();
    }

    public QueryContextBox getParent() {
        return parent;
    }
}
