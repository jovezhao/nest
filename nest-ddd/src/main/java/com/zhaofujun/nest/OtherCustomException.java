package com.zhaofujun.nest;

public class OtherCustomException extends CustomException {
    public OtherCustomException(String message, Throwable cause, Object... arguments) {
        super(100, message, cause, arguments);

    }


}
