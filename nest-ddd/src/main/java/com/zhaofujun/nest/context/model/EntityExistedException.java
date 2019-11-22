package com.zhaofujun.nest.context.model;

import com.zhaofujun.nest.CustomException;
import com.zhaofujun.nest.core.ExceptionCode;

public class EntityExistedException extends CustomException {
    public EntityExistedException(String message, Object... arguments) {
        super(ExceptionCode.EntityExisted, message, arguments);
    }
}
