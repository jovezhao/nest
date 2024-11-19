package com.zhaofujun.nest.ddd.event;

import java.util.List;

public interface EventMessageQuery {

    List<Long> getListToBeSent(int commonSize,int failSize, int maxFailTime);

}
