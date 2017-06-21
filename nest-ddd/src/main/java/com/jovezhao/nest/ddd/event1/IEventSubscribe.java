package com.jovezhao.nest.ddd.event1;

public  interface IEventSubscribe{
   void subscribe(String eventName, IEventHandler handler);
   void stop();
}
