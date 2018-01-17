package com.duan.blogos.exception.internal;

/**
 * Created on 2017/12/26.
 * 缺失请求参数
 *
 * @author DuanJiaNing
 */
public class MissingRequestParameterException extends BaseRuntimeException {

    private static final int code = 16;

    public MissingRequestParameterException() {
        super(code);
    }

    public MissingRequestParameterException(String message) {
        super(message, code);
    }

    public MissingRequestParameterException(String message, Throwable cause) {
        super(message, cause, code);
    }

}