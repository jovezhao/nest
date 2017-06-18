package com.jovezhao.nest.starter;

import com.jovezhao.nest.utils.SpringUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Created by zhaofujun on 2017/6/16.
 */
@Component
public class ApplicationContextUtils implements ApplicationContextAware {
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringUtils.setApplicationContext(applicationContext);
    }
}
