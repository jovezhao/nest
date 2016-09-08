package com.ywkj.nest.ddd;

public  interface IEventSubscribe{
   void subscribe(String eventName, IEventHandler handler);
   void stop();
}
