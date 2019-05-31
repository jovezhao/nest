package com.guoshouxiang.nest.spring.test;

import com.guoshouxiang.nest.NestApplication;
import com.guoshouxiang.nest.container.ContainerProvider;
import com.guoshouxiang.nest.context.event.EventBus;
import com.guoshouxiang.nest.event.ApplicationEvent;
import com.guoshouxiang.nest.event.ApplicationListener;
import com.guoshouxiang.nest.event.ServiceContextListener;
import com.guoshouxiang.nest.event.ServiceEvent;
import com.guoshouxiang.nest.spring.test.appservices.TestAppservices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.lang.reflect.Method;


public class Application {


    //设置
    public static void main(String[] args) {

        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        ContainerProvider beanContainerProvider = applicationContext.getBean(ContainerProvider.class);
        NestApplication nestApplication =applicationContext.getBean(NestApplication.class);// new NestApplication(beanContainerProvider);
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


        Application application = applicationContext.getBean(Application.class);
        application.run();

    }

    @Autowired
    private EventBus eventBus;
    @Autowired
    private TestAppservices testAppservices;

    @Autowired
    ContainerProvider containerProvider;

    public void run() {
        eventBus.autoRegister();
        testAppservices.createUser("111", "pwd");
    }
}


