package com.github.yungyu16.version.util;

import org.apache.maven.plugin.logging.Log;

/**
 * maven构建日志的全局委托工具类
 * CreatedDate: 2020/5/8
 * Author: yunyu16
 */
public abstract class LogUtil {
    private static Log log;

    private LogUtil() {
    }

    /**
     * 初始化委托
     *
     * @param thatLog
     */
    public static void initDelegateLog(Log thatLog) {
        log = thatLog;
    }

    public static boolean isDebugEnabled() {
        return log.isDebugEnabled();
    }

    public static void debug(CharSequence content) {
        log.debug(content);
    }

    public static void debug(CharSequence content, Throwable error) {
        log.debug(content, error);
    }

    public static void debug(Throwable error) {
        log.debug(error);
    }

    public static boolean isInfoEnabled() {
        return log.isInfoEnabled();
    }

    public static void info(CharSequence content) {
        log.info(content);
    }

    public static void info(CharSequence content, Throwable error) {
        log.info(content, error);
    }

    public static void info(Throwable error) {
        log.info(error);
    }

    public static boolean isWarnEnabled() {
        return log.isWarnEnabled();
    }

    public static void warn(CharSequence content) {
        log.warn(content);
    }

    public static void warn(CharSequence content, Throwable error) {
        log.warn(content, error);
    }

    public static void warn(Throwable error) {
        log.warn(error);
    }

    public static boolean isErrorEnabled() {
        return log.isErrorEnabled();
    }

    public static void error(CharSequence content) {
        log.error(content);
    }

    public static void error(CharSequence content, Throwable error) {
        log.error(content, error);
    }

    public static void error(Throwable error) {
        log.error(error);
    }
}
