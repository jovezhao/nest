package com.ywkj.nest.core;

/**
 * Created by Jove on 2017/2/8.
 */
public class PageParames {
    private PageParames(long startIndex, int count) {
        this.startIndex = startIndex;
        this.count = count;
    }

    public static PageParames create(long startIndex, int pageSize) {
        return new PageParames(startIndex, pageSize);
    }

    private long startIndex;
    private int count;


    public long getStartIndex() {
        return startIndex;
    }

    public int getCount() {
        return count;
    }

}
