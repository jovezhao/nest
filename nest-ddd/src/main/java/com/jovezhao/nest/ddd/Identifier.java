package com.jovezhao.nest.ddd;

import java.io.Serializable;

/**
 * Created by zhaofujun on 2017/6/20.
 */

public abstract class Identifier implements Serializable {
    public abstract String toValue();
}
