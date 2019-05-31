package com.guoshouxiang.nest.context.event.channel;

import com.guoshouxiang.nest.context.event.EventHandler;


public interface  MessageConsumer {



      void subscribe(EventHandler eventHandler);


}
