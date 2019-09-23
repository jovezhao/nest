package com.zhaofujun.nest.context.model;

import com.zhaofujun.nest.CustomException;

public class VerifyFailedException extends CustomException {
    public VerifyFailedException(String message, Object... arguments) {
        super(10000, message, arguments);
    }
}
