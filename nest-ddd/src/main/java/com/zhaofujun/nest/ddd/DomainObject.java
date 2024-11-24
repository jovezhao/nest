package com.zhaofujun.nest.ddd;

/**
 * 表示领域对象的抽象类。
 * 领域对象是领域驱动设计（DDD）中的一个基本概念，它代表了业务领域中的一个实体或值对象。
 */
public  interface DomainObject {
    /**
     * 获取当前对象的类名。
     * 
     * @return 当前对象的类名。
     */
    default String getClassName() {
        final String PROXY_SPLIT_STR = "$$";
        final int BEGIN_INDEX = 0;

        String className = this.getClass().getName();
        int endIndex = className.indexOf(PROXY_SPLIT_STR);
        return endIndex == -1 ? className : className.substring(BEGIN_INDEX, endIndex);
    }

    /**
     * 获取当前对象的简单类名。
     * 
     * @return 当前对象的简单类名。
     */
    default String getClassSimpleName() {
        final String PROXY_SPLIT_STR = "$$";
        final int BEGIN_INDEX = 0;

        String className = this.getClass().getSimpleName();
        int endIndex = className.indexOf(PROXY_SPLIT_STR);
        return endIndex == -1 ? className : className.substring(BEGIN_INDEX, endIndex);
    }
}
