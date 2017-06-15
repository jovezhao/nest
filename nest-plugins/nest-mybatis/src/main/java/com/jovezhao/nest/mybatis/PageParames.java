package com.jovezhao.nest.mybatis;

/**
 * 分页参数
 * Created by Jove on 2017/2/8.
 */
public class PageParames {
    private PageParames(int pageIndex, int pageSize) {
        if (pageIndex < 1)
            this.pageIndex = 1;
        else
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
