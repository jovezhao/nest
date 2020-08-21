package com.zhaofujun.nest.exception;

import com.zhaofujun.nest.standard.CustomException;

public class OtherCustomException extends CustomException {
    public OtherCustomException(String message, Throwable cause, Object... arguments) {
        super(100, message, cause, arguments);

    }


}
