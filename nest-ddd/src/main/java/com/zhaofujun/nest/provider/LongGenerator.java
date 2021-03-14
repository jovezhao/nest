package com.zhaofujun.nest.provider;

public interface LongGenerator  {



    String getName();

    Long nextValue(String type);
}
