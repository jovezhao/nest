package com.zhaofujun.nest.ddd.event;

import java.util.List;

public interface EventInfoQuery {

    List<Long> getPersend(int commonSize,int failSize, int maxFailTime);

}
