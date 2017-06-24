package com.jovezhao.nest.exception;

/**
 * 用于不可控但往往会影响业务流程的异常，常见于网络原因或第三方系统出现故障。
 * Created by zhaofujun on 2017/6/23.
 */
public class SystemException extends RuntimeException {

    public SystemException(String message) {
        super(message);
    }

    public SystemException(String message, Throwable cause) {
        super(message, cause);
    }
}
