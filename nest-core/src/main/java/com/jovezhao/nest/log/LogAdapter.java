package com.jovezhao.nest.log;

import com.jovezhao.nest.exception.CustomException;
import com.jovezhao.nest.exception.SystemException;

import java.io.Serializable;

/**
 * Created by zhaofujun on 2017/6/23.
 */
public class LogAdapter implements Log,Serializable {
    public LogAdapter(Class clazz) {
        logger = org.slf4j.LoggerFactory.getLogger(clazz);
    }

    private org.slf4j.Logger logger;

    @Override
    public void error(Exception ex) {
        logger.error(ex.getMessage(), ex);
    }

    @Override
    public void warn(SystemException ex) {

        logger.error(ex.getMessage(), ex);
    }
     @Override
    public void warn(Exception ex) {

        logger.error(ex.getMessage(), ex);
    }


    @Override
    public void info(String format, Object... arguments) {
        logger.info(format, arguments);
    }

    @Override
    public void debug(String format, Object... arguments) {
        logger.debug(format, arguments);
    }

    @Override
    public void trace(CustomException ex) {
        logger.trace(ex.toString());
    }
}
