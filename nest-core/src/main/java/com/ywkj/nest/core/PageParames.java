package com.ywkj.nest.core;

/**
 * Created by Jove on 2017/2/8.
 */
public class PageParames {
    private PageParames(int pageIndex, int pageSize) {
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
    }

    public static PageParames create(int pageIndex, int pageSize) {
        return new PageParames(pageIndex, pageSize);
    }

    private int pageIndex;
    private int pageSize;


    public int getPageIndex() {
        return pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }
}
