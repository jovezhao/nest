package com.zhaofujun.nest.ddd.event;

import java.util.List;

import com.zhaofujun.nest.ddd.Query;

public interface EventMessageQuery extends Query {

    List<Long> getListToBeSent(int commonSize,int failSize, int maxFailTime);

}
