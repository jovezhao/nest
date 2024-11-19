package com.zhaofujun.nest.ddd.event;

import java.io.Serializable;

/**
 * 事件数据类，用于封装事件的数据。
 * 该类将被序列化后通过事件通道传送
 *
 * @param <T> 事件数据类型
 */
public class EventData<T> implements Serializable {
    /**
     * 事件ID。
     */
    private String id;

    /**
     * 事件数据。
     */
    private T data;

    /**
     * 获取事件ID。
     *
     * @return 事件ID
     */
    public String getId() {
        return id;
    }

    /**
     * 设置事件ID。
     *
     * @param id 事件ID
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取事件数据。
     *
     * @return 事件数据
     */
    public T getData() {
        return data;
    }

    /**
     * 设置事件数据。
     *
     * @param data 事件数据
     */
    public void setData(T data) {
        this.data = data;
    }

}
