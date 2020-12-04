package com.zhaofujun.nest.exception;

import com.zhaofujun.nest.standard.CustomException;

public class VersionConflictedException  extends CustomException {

    public VersionConflictedException(String message, Object... arguments) {
        super(ExceptionCode.VersionConflicted, message, arguments);
    }

    public VersionConflictedException(String message, Throwable cause, Object... arguments) {
        super(ExceptionCode.VersionConflicted, message, cause, arguments);
    }
}
