package com.ywkj.nest.ddd.event;

public  interface IEventSubscribe{
   void subscribe(String eventName, IEventHandler handler);
   void stop();
}
