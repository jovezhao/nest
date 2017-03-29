package com.ywkj.nest.core.exception;

/**
 * 系统异常基类，用于把异常转换为运行时异常
 *
 * @author Jove
 */
public class SystemException extends RuntimeException {

    public SystemException(Exception exception) {
        super(exception.getMessage(), exception.getCause());
    }

}

