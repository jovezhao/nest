package com.zhaofujun.nest3.application.listener;

public class ApplicationListener {
    public interface ApplicationBeforeStartListener extends NestEventListener<NestApplicationEventObject> {

    }

    public interface ApplicationStartedListener extends NestEventListener<NestApplicationEventObject> {

    }

    public interface ApplicationBeforeCloseListener extends NestEventListener<NestApplicationEventObject> {

    }

    public interface ApplicationClosedListener extends NestEventListener<NestApplicationEventObject> {

    }
}