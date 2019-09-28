package com.zhaofujun.nest.rockermq;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RockerMqProperties {

    private String brokers;

    private String groupName;

    private String nameSpace;

    private Boolean vipChannelEnable=false;

    /**
     * 同时消费信息数
     */
    private int consumeMessageBatchMaxSize=1;
}
