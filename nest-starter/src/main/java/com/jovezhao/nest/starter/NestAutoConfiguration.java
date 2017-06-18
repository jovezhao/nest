package com.jovezhao.nest.starter;

import com.jovezhao.nest.ddd.IUnitOfWork;
import com.jovezhao.nest.ddd.NestUnitOfWork;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by zhaofujun on 2017/6/17.
 */
@Configuration
@ComponentScan
public class NestAutoConfiguration {


    @Bean
    public IUnitOfWork getUnitOfWork(){
        return new NestUnitOfWork();
    }
}
