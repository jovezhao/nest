package com.zhaofujun.nest.provider;

public interface LongGenerator extends Provider {
    Long nextValue(String key);
}
