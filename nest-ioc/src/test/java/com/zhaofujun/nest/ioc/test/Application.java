package com.zhaofujun.nest.ioc.test;

import com.zhaofujun.nest.NestApplication;
import com.zhaofujun.nest.container.ContainerProvider;
import com.zhaofujun.nest.core.EventBus;
import com.zhaofujun.nest.event.ApplicationEvent;
import com.zhaofujun.nest.event.ApplicationListener;
import com.zhaofujun.nest.event.ServiceContextListener;
import com.zhaofujun.nest.event.ServiceEvent;
import com.zhaofujun.nest.ioc.annotation.Component;
import com.zhaofujun.nest.ioc.annotation.Autowired;
import com.zhaofujun.nest.ioc.config.IocConfiguration;
import com.zhaofujun.nest.ioc.test.appservices.TestAppservices;

import java.lang.reflect.Method;

@Component
public class Application   {


    public Application() {


    }

    @Autowired
    private TestAppservices testAppservices;

    @Autowired
    private EventBus eventBus;

    @Autowired
    private NestApplication nestApplication;


    //设置
    public static void main(String[] args) {

        IocConfiguration iocConfiguration = new IocConfiguration("com.zhaofujun.nest.ioc.test");
        iocConfiguration.init();



        ContainerProvider containerProvider = iocConfiguration.getContainerProvider();

        NestApplication nestApplication = containerProvider.getInstance(NestApplication.class);

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



        Application application = containerProvider.getInstance(Application.class);
        application.run();

    }


    public void run() {

        nestApplication.start();

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



        eventBus.autoRegister();
        testAppservices.createUser("111", "pwd");

        nestApplication.close();

    }
}


