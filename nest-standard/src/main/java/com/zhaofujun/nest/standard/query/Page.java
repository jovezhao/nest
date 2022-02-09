package com.zhaofujun.nest.standard.query;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Page<T> {
    private List<T> list;
    private int size;
    private long totalCount;
    private long currentPage;

    public List<T> getList() {
        return list;
    }

    public int getSize() {
        return size;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public long getTotalPages() {
        return totalCount / size + 1;
    }

    public Page(int size, long currentPage) {
        this.size = size;
        this.currentPage = currentPage;
    }

    public Page(Pageable pageable) {
        this.size = pageable.getSize();
        this.size = pageable.getSize();
    }

    public Pageable getPageable() {
        return new Pageable(this.currentPage, this.size);
    }

    public void setData(List<T> list, long totalCount) {
        this.list = list;
        this.totalCount = totalCount;
    }

    public <U> Page<U> map(Function<? super T, ? extends U> converter) {
        Page<U> uPage = new Page<>(this.size, this.currentPage);
        uPage.setData(list.stream().map(converter).collect(Collectors.toList()), this.totalCount);
        return uPage;
    }
}

