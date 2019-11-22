package com.zhaofujun.nest;

import com.zhaofujun.nest.core.ExceptionCode;

public class NullException extends CustomException {
    public NullException( String message, Object... arguments) {
        super(ExceptionCode.Null, message, arguments);
    }
}
