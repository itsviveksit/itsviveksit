package com.org.khatabahi.common.exception;

public class BaseRuntimeException extends RuntimeException{

    public BaseRuntimeException() {
        super();
    }

    public BaseRuntimeException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public BaseRuntimeException(String s) {
        super(s);
    }

    public BaseRuntimeException(Throwable throwable) {
        super(throwable);
    }
}
