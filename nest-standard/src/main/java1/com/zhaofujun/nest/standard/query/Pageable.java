package com.zhaofujun.nest.standard.query;

public class Pageable {
    private int size;
    private long currentPage;

    public Pageable(long currentPage, int size) {
        this.size = size;
        this.currentPage = currentPage;
    }

    public int getSize() {
        return size;
    }

    public long getCurrentPage() {
        return currentPage;
    }
}
