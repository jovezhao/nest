package com.jovezhao.nest.log;

import com.jovezhao.nest.exception.CustomException;
import com.jovezhao.nest.exception.SystemException;
import org.slf4j.Logger;

/**
 * Created by zhaofujun on 2017/6/23.
 */
public interface Log{
    /**
     * 意料之外的异常，需要运维关注，程序员修复的异常。对外以系统异常报告
     * @param ex
     */
    void error(Exception ex);

    /**
     * 不需要程序员修复，但需要运维关注以保证业务完整，如第三方系统对接出错，网络中断等。对外以系统异常报告
     * @param ex
     */
    void warn(SystemException ex);
    void warn(Exception ex);

    /**
     * 运行时产生的重要业务数据记录，如调用外部系统的参数传递及响应内容等
     * @param format
     * @param arguments
     */
    void info(String format, Object... arguments);

    /**
     * 用于开发期间调试程序时需要关注的数据，常用于控制台输出
     * @param format
     * @param arguments
     */
    void debug(String format, Object... arguments);

    /**
     * 业务异常，属于正常情况，如用户登录时提示用户名密码不正确，对外以业务异常报告
     * @param ex
     */
    void trace(CustomException ex);
}
