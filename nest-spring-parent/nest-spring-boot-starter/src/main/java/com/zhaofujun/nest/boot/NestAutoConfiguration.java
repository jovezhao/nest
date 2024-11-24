package com.zhaofujun.nest.boot;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import com.zhaofujun.nest.ddd.context.Transaction;
import com.zhaofujun.nest.ddd.event.EventAppService;
import com.zhaofujun.nest.ddd.event.EventMessageQuery;
import com.zhaofujun.nest.ddd.event.EventMessageRepository;
import com.zhaofujun.nest.inner.DefaultEventInfoRepository;

@Import(NestBeanScannerRegistrar.class)
@AutoConfiguration
@ComponentScan
public class NestAutoConfiguration {

    public NestAutoConfiguration() {
        System.out.println("NestAutoConfiguration init");
    }

    @Bean
    public EventMessageRepository eventInfoQuery() {
        return new DefaultEventInfoRepository();
    }

    @Bean
    public EventAppService eventAppService(EventMessageQuery eventInfoQuery) {
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
    // return new SpringTransaction();
    // }
}
