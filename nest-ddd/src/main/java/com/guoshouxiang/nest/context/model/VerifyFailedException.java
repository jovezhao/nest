package com.guoshouxiang.nest.context.model;

import com.guoshouxiang.nest.CustomException;

public class VerifyFailedException extends CustomException {
    public VerifyFailedException(String message, Object... arguments) {
        super(10000, message, arguments);
    }
}
