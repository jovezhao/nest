package com.ywkj.nest.core;

/**
 * Created by Jove on 2017/2/8.
 */
public class PageParames {
    private PageParames(long startIndex, int count) {
        this.startIndex = startIndex;
        this.count = count;
    }

    public static PageParames create(long startIndex, int count) {
        return new PageParames(startIndex, count);
    }

    private long startIndex;
    private int count;
    private int totalCount;

    public long getStartIndex() {
        return startIndex;
    }

    public int getCount() {
        return count;
    }

    public int getTotalCount() {
        return totalCount;
    }
}
