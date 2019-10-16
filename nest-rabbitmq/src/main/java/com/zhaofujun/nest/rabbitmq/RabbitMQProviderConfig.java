package com.zhaofujun.nest.rabbitmq;


import com.rabbitmq.client.BuiltinExchangeType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

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

    /**
     * 默认为广播模式
     */
    private BuiltinExchangeType exchangeType=BuiltinExchangeType.FANOUT;


    /**
     * 交换机名称
     */
    private String exchangeName="default-exchange";

    /**
     * 路由KEY
     */
    private String routingKey="";


    /**
     * 队列存活最大时间
     */
    private Long xMessageTTL=60000L;


    private Map<String,Object> arguments;


    public Map<String, Object> getArguments() {
        if(null==this.arguments||this.arguments.isEmpty()){
            return this.defaultArguments();
        }
        return this.arguments;
    }

    public Map<String,Object> defaultArguments(){
        this.arguments=new HashMap<>();
        this.arguments.put("x-message-ttl",this.xMessageTTL);
        return arguments;
    }
}
