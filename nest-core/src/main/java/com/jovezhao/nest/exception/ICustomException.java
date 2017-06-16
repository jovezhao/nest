package com.jovezhao.nest.exception;

/**
 * 自定义的异常基类
 *
 * @author Jove
 */
public interface ICustomException {

    String getMessage();

    int getCode();

    Object[] getParames();


    Object getOutParame();

    void setOutParame(Object outParame);


}

