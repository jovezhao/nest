package com.zhaofujun.nest3;

public class NestCustomException extends CustomException{
    public static final int VerifyFailed = 10001;
    public static final int EntityExisted = 10002;
    public static final int VersionConflicted = 10003;
    public static final int Null = 10004;
    public static final int JsonAnalyseFailed = 10005;
    public static final int Other=10006;

    public NestCustomException(int errorCode, String message, Object... arguments) {
        super(errorCode, message, arguments);
    }

    public NestCustomException(int errorCode, String message, Throwable cause, Object... arguments) {
        super(errorCode, message, cause, arguments);
    }
}
