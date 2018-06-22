package com.ty.study.proxy.exception;

/**
 * 实例无法代理
 *
 * @author relax tongyu
 * @create 2018-02-09 10:44
 **/
public class ObjectUnenableProxyException extends Exception{

    public ObjectUnenableProxyException() {
        super();
    }

    public ObjectUnenableProxyException(String message) {
        super(message);
    }

    public ObjectUnenableProxyException(String message, Throwable cause) {
        super(message, cause);
    }



    public ObjectUnenableProxyException(Throwable cause) {
        super(cause);
    }

    protected ObjectUnenableProxyException(String message, Throwable cause,
                                           boolean enableSuppression,
                                           boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
