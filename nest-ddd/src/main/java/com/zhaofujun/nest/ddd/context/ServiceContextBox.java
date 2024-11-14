package com.zhaofujun.nest.ddd.context;

import java.util.Stack;

public class ServiceContextBox {
    private long threadId;
    private Stack<ServiceContext> stack;
    private ServiceContextBox parent;

    public ServiceContextBox(ServiceContext serviceContext, ServiceContextBox parent) {
        this.threadId = Thread.currentThread().getId();
        this.stack = new Stack<>();
        this.stack.push(serviceContext);
        this.parent = parent;
    }

    public boolean onlyOne() {
        return stack.size() == 1;
    }

    public ServiceContext get() {
        return stack.peek();
    }

    public ServiceContext pop() {
        return stack.pop();
    }

    public ServiceContext push(ServiceContext serviceContext) {
        return stack.push(serviceContext);
    }

    public long getThreadId() {
        return threadId;
    }

    public boolean owner(){
       return this.threadId==Thread.currentThread().getId();
    }

    public ServiceContextBox getParent() {
        return parent;
    }
}
