package com.zhaofujun.nest.test;

import com.zhaofujun.nest.NestApplication;
import com.zhaofujun.nest.context.appservice.ApplicationServiceCreator;
import com.zhaofujun.nest.context.appservice.EntityOperateEnum;
import com.zhaofujun.nest.context.event.DefaultEventBus;
import com.zhaofujun.nest.context.event.channel.MessageChannelProvider;
import com.zhaofujun.nest.context.event.message.MessageInfo;
import com.zhaofujun.nest.context.model.BaseEntity;
import com.zhaofujun.nest.event.EventBusListener;
import com.zhaofujun.nest.event.ServiceContextListener;
import com.zhaofujun.nest.event.ServiceEvent;
import com.zhaofujun.nest.standard.Repository;
import com.zhaofujun.nest.test.adapter.EntityNotifyEventHandler;
import com.zhaofujun.nest.test.application.TeacherApplicationService;
import com.zhaofujun.nest.test.application.UserApplicationService;

import java.io.IOException;
import java.util.List;
import java.util.Map;


public class Application {
    public static void main(String[] args) throws IOException, InterruptedException {

        NestApplication application = NestApplication.current();
//        application.getListenerManager().addListeners(new TestServiceContextListener());
        application.getListenerManager().addListeners(new TestEventBusListener());
         new DefaultEventBus().registerHandler(new EntityNotifyEventHandler());
        application.start();

//        TeacherApplicationService teacherApplicationService = ApplicationServiceCreator.create(TeacherApplicationService.class);
//        teacherApplicationService.teachCreate();

        UserApplicationService userApplicationService=ApplicationServiceCreator.create(UserApplicationService.class);
        userApplicationService.create();

        application.close();

////        application.getRepositoryManager().addRepository(new UserRepository());
//        for (int i = 0; i < 100; i++) {
//            Thread thread2 = new Thread(new TestRunnable(), "thread" + i);
//            thread2.start();
//        }
//
//
//        System.in.read();
//
//        long s = System.currentTimeMillis();
//        for (int i = 0; i < 0; i++) {
//            userService.change(i);
//        }
//        Long e = System.currentTimeMillis();
//        System.out.println("耗时：" + (e - s));
        //application.close();
//        System.out.println("end");
//        String js="{\"CGLIB$BOUND\":true,\"CGLIB$CALLBACK_0\":null,\"name\":\"name1\",\"age\":11,\"id\":{\"id\":10},\"_version\":0,\"__type__\":\"com.zhaofujun.nest.test.domain.User\"}";
//        JsonCreator jsonCreator=new JsonCreator();
//        User user = jsonCreator.toObj(js, User.class);
    }

    static class TestRunnable implements Runnable {

        @Override
        public void run() {
            for (int i = 0; i < 5; i++) {
                UserApplicationService userService = ApplicationServiceCreator.create(UserApplicationService.class);
                try {
                    userService.create();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class TestServiceContextListener implements ServiceContextListener {
        @Override
        public void beforeEntityCommit(ServiceEvent serviceEvent, Map<Repository, Map<EntityOperateEnum, List<BaseEntity>>> entityMaps) {

        }
    }

    static class TestEventBusListener implements EventBusListener {

        @Override
        public void beforePublish(MessageChannelProvider messageChannelProvider, MessageInfo messageInfo) {
            System.out.println("beforePublish" + messageChannelProvider + ":" + messageInfo);
        }

        @Override
        public void onReceived(MessageChannelProvider messageChannelProvider, MessageInfo messageInfo) {
            System.out.println("onReceived" + messageChannelProvider + ":" + messageInfo);

        }
    }
}
