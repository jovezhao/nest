package com.zhaofujun.nest.exception;

import com.zhaofujun.nest.standard.CustomException;

public class VerifyFailedException extends CustomException {
    public VerifyFailedException(String message, Object... arguments) {
        super(ExceptionCode.VerifyFailed, message, arguments);
    }

}
