package com.zhaofujun.nest3.model;


public abstract class DomainObject {
    public String getClassName() {
        final String PROXY_SPLIT_STR = "$$";
        final int BEGIN_INDEX = 0;

        String className = this.getClass().getName();
        int endIndex = className.indexOf(PROXY_SPLIT_STR);
        return endIndex == -1 ? className : className.substring(BEGIN_INDEX, endIndex);
    }

    public String getClassSimpleName() {
        final String PROXY_SPLIT_STR = "$$";
        final int BEGIN_INDEX = 0;

        String className = this.getClass().getSimpleName();
        int endIndex = className.indexOf(PROXY_SPLIT_STR);
        return endIndex == -1 ? className : className.substring(BEGIN_INDEX, endIndex);
    }
}
