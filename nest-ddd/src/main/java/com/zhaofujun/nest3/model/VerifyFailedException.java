package com.zhaofujun.nest3.model;


import com.zhaofujun.nest3.CustomException;

public class VerifyFailedException extends CustomException {
    public VerifyFailedException(String message, Object... arguments) {
        super(10001, message, arguments);
    }

}
