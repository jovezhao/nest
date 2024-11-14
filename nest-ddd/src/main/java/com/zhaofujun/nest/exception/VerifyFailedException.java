package com.zhaofujun.nest.exception;

public class VerifyFailedException extends CustomException {
    public VerifyFailedException(String message, Object... arguments) {
        super(10001, message, arguments);
    }

}
