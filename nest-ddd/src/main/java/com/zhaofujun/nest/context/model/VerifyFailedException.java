package com.zhaofujun.nest.context.model;

import com.zhaofujun.nest.CustomException;
import com.zhaofujun.nest.core.ExceptionCode;

public class VerifyFailedException extends CustomException {
    public VerifyFailedException(String message, Object... arguments) {
        super(ExceptionCode.VerifyFailed, message, arguments);
    }

}
