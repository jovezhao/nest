package com.jovezhao.nest;

import com.jovezhao.nest.utils.SpringUtils;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;

/**
 * Created by Jove on 2016/11/15.
 */
public class NestContextLoaderListener extends ContextLoaderListener {
    @Override
    public WebApplicationContext initWebApplicationContext(ServletContext servletContext) {
        WebApplicationContext webApplicationContext= super.initWebApplicationContext(servletContext);
        SpringUtils.setApplicationContext(webApplicationContext);
        return webApplicationContext;
    }
}
