package com.zhaofujun.nest3;

public class NestSystemException extends SystemException {

    public NestSystemException(String message) {
        super(message);
    }

    public NestSystemException(String message, Throwable cause) {
        super(message, cause);
    }
}
