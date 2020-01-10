package com.zhaofujun.nest;


/**
 * 自定义异常，与业务相关，如用户名或密码不匹配等
 * Created by zhaofujun on 2017/6/23.
 */
public abstract class CustomException extends RuntimeException {
    private int errorCode;

    public CustomException(int errorCode, String message, Object... arguments) {
        super(message);
        this.errorCode = errorCode;
        Object[] arguments1 = arguments;
    }

    public CustomException(int errorCode, String message, Throwable cause, Object... arguments) {
        super(message, cause);
        this.errorCode = errorCode;
        Object[] arguments1 = arguments;
    }

    public int getErrorCode() {
        return errorCode;
    }

//    public String toString() {
//        StringBuilder stringBuilder = new StringBuilder(this.getMessage() + "\r\n");
//        stringBuilder.append("errorCode:" + errorCode + "\r\n");
//        stringBuilder.append("输入参数：\r\n");
//        for (Object argument : arguments) {
//            stringBuilder.append("\t" + JsonUtils.toJsonString(argument) + "\r\n");
//        }
//        return stringBuilder.toString();
//    }

}
