package com.zhaofujun.nest.exception;

import com.zhaofujun.nest.standard.CustomException;

public class EntityExistedException extends CustomException {
    public EntityExistedException(String message, Object... arguments) {
        super(ExceptionCode.EntityExisted, message, arguments);
    }
}
