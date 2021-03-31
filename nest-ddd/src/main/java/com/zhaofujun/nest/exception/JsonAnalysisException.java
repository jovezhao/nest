package com.zhaofujun.nest.exception;

import com.zhaofujun.nest.standard.CustomException;

public class JsonAnalysisException extends CustomException {
    public JsonAnalysisException( String message, Object... arguments) {
        super(ExceptionCode.JsonAnalyseFailed, message, arguments);
    }
}
