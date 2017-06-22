package com.jovezhao.nest.test.api;

import java.io.Serializable;

/**
 * Created by zhaofujun on 2017/6/23.
 */
public class TestDto implements Serializable {
    private String abs;

    public String getAbs() {
        return abs;
    }

    public void setAbs(String abs) {
        this.abs = abs;
    }
}
