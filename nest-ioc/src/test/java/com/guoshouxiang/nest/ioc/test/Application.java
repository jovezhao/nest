package com.guoshouxiang.nest.ioc.test;

import com.guoshouxiang.nest.NestApplication;
import com.guoshouxiang.nest.context.event.EventBus;
import com.guoshouxiang.nest.event.ApplicationEvent;
import com.guoshouxiang.nest.event.ApplicationListener;
import com.guoshouxiang.nest.event.ServiceContextListener;
import com.guoshouxiang.nest.event.ServiceEvent;
import com.guoshouxiang.nest.ioc.DefaultContainerProvider;
import com.guoshouxiang.nest.ioc.annotation.Component;
import com.guoshouxiang.nest.ioc.annotation.Inject;
import com.guoshouxiang.nest.ioc.test.appservices.TestAppservices;

import java.lang.reflect.Method;

@Component
public class Application {


    public Application() {


    }

    @Inject
    private TestAppservices testAppservices;

    @Inject
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


