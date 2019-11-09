package com.zhaofujun.nest.ioc.test;

import com.zhaofujun.nest.NestApplication;
import com.zhaofujun.nest.context.event.EventBus;
import com.zhaofujun.nest.event.ApplicationEvent;
import com.zhaofujun.nest.event.ApplicationListener;
import com.zhaofujun.nest.event.ServiceContextListener;
import com.zhaofujun.nest.event.ServiceEvent;
import com.zhaofujun.nest.ioc.DefaultContainerProvider;
import com.zhaofujun.nest.ioc.annotation.Component;
import com.zhaofujun.nest.ioc.annotation.Autowired;
import com.zhaofujun.nest.ioc.test.appservices.TestAppservices;

import java.lang.reflect.Method;

@Component
public class Application {


    public Application() {


    }

    @Autowired
    private TestAppservices testAppservices;

    @Autowired
    EventBus eventBus;

    public void run() {
        eventBus.autoRegister();
        testAppservices.createUser("111", "pwd");

    }

    //设置
    public static void main(String[] args) {

        DefaultContainerProvider beanContainerProvider = new DefaultContainerProvider("com.guoshouxiang.nest");
        NestApplication nestApplication = new NestApplication(beanContainerProvider);
        nestApplication.addApplicationListener(new ApplicationListener() {
            @Override
            public void applicationStarted(ApplicationEvent applicationEvent) {
                System.out.println("应用启动");
            }

            @Override
            public void applicationClosed(ApplicationEvent applicationEvent) {
                System.out.println("应用停止");

            }
        });
        nestApplication.addServiceContextListener(new ServiceContextListener() {
            @Override
            public void serviceCreated(ServiceEvent serviceEvent) {
                System.out.println("serviceCreated");
            }

            @Override
            public void serviceMethodStart(ServiceEvent serviceEvent, Method method) {
                System.out.println("serviceMethodStart");

            }

            @Override
            public void serviceMethodEnd(ServiceEvent serviceEvent, Method method) {
                System.out.println("serviceMethodEnd");

            }

            @Override
            public void beforeCommit(ServiceEvent serviceEvent) {
                System.out.println("beforeCommit");

            }

            @Override
            public void committed(ServiceEvent serviceEvent) {
                System.out.println("committed");

            }

            @Override
            public void serviceEnd(ServiceEvent serviceEvent) {
                System.out.println("serviceEnd");

            }
        });
        nestApplication.start();

        Application application = beanContainerProvider.getInstance(Application.class);
        application.run();


    }
}


