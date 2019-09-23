package com.zhaofujun.nest;

public class NullException extends CustomException {
    public NullException( String message, Object... arguments) {
        super(1000, message, arguments);
    }
}
