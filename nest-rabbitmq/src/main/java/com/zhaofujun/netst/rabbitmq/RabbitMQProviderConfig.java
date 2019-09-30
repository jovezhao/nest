package com.zhaofujun.netst.rabbitmq;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by zhaofujun on 2017/6/22.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RabbitMQProviderConfig  {
    private String host;
    private int port;
    private String user;
    private String pwd;

    //设置客户端最多接受未被ack的消息的个数
    private int prefetchCount=5;

}
