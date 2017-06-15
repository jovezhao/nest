package com.jovezhao.nest.log;


import com.jovezhao.nest.exception.CustomException;

/**
 * <p/>
 * DEBUG： 记录开发时的调试信息，用于开发过程中使用，正式环境不需要配置该级别。
 * <p/>
 * INFO： 记录一般信息，用于记录重要的业务处理过程。
 * <p/>
 * WARN:  记录警告信息，用于记录一些没有处理成功但也无关紧要的业务过程
 * <p/>
 * ERROR: 记录业务失败信息，用于业务处理失败时，由系统框架自动处理
 * <p/>
 * FATAL： 记录系统异常，用于底层异常，由系统框架自动处理
 *
 * Created by Jove on 2016-03-17.
 */
public interface ILog {
    void debug(String message, Object... args);
    void info(String message, Object... args);
    void warn(String message, Object... args);
    void error(CustomException ex);
    void fatal(Exception ex, Object... args);
}
