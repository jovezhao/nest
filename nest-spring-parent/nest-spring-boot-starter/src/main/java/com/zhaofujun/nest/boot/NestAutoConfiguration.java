package com.zhaofujun.nest.boot;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.zhaofujun.nest.ddd.context.Transaction;
import com.zhaofujun.nest.ddd.event.EventAppService;
import com.zhaofujun.nest.ddd.event.EventInfoQuery;
import com.zhaofujun.nest.inner.DefaultEventInfoRepostory;

@Configuration
@ComponentScan
@Import(NestBeanScannerRegistrar.class)
public class NestAutoConfiguration {
    @Bean
    public EventInfoQuery eventInfoQuery() {
        return new DefaultEventInfoRepostory();
    }

    @Bean
    public EventAppService eventAppService(EventInfoQuery eventInfoQuery) {
        EventAppService eventAppService = new EventAppService();
        eventAppService.setQuery(eventInfoQuery);
        return eventAppService;
    }

    @Bean
    public Transaction.DefaultTransaction defaultTransaction() {
        return new Transaction.DefaultTransaction();
    }

    // @Bean
    // public SpringTransaction springTransaction(){
    //     return new SpringTransaction();
    // }
}
