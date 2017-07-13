package com.jovezhao.nest.test;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Created by zhaofujun on 2017/6/17.
 */

@SpringBootApplication
@MapperScan("com.jovezhao.nest.test.repositories.mappers")
public class Application extends SpringBootServletInitializer {


    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

    }
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }
//    @Autowired
//    private UserService userService;
//
//    @Override
//    public void run(String[] args) throws Exception {
////        ChannelProvider channelProvider = new ActiveMQChannelProvider();
////
////        ActiveMQProviderConfig providerConfig = new ActiveMQProviderConfig();
////        providerConfig.setBrokers("tcp://127.0.0.1:61616");
////        channelProvider.setProviderConfig(providerConfig);
////        channelProvider.init();
////
////        EventConfigItem eventConfigItem = new EventConfigItem();
////        eventConfigItem.setEventName("event1");
////        eventConfigItem.setChannelProvider(channelProvider);
////        EventConfigManager.put(eventConfigItem);
////
////
////        EventBus.registerHandler(new TestHandler());
////        EventBus.registerHandler(new TestHandler());
////
////
////        userService.changeName("new 55");
////
//
//    }
//
//    public class TestHandler implements EventHandler<TestDto> {
//        private Log log = new LogAdapter(this.getClass());
//
//        @Override
//        public String getEventName() {
//            return "event1";
//        }
//
//        @Override
//        public Class<TestDto> getTClass() {
//            return TestDto.class;
//        }
//
//        @Override
//        public void handle(TestDto data) throws Exception {
//            log.info(data.getAbs());
//            //System.out.println("fffffff::" + data.getAbs());
//        }
//    }
}
