package com.zhaofujun.nest.exception;

import com.zhaofujun.nest.standard.CustomException;

public class NullException extends CustomException {
    public NullException( String message, Object... arguments) {
        super(ExceptionCode.Null, message, arguments);
    }
}
