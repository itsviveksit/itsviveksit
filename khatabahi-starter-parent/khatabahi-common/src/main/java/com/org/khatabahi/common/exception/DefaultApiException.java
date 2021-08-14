package com.org.khatabahi.common.exception;

import com.org.khatabahi.common.constant.StatusCode;

public class DefaultApiException extends RuntimeException{
    private String errorCode;
    private String message;

    public DefaultApiException() {
        super();
    }

    public DefaultApiException(String message, Throwable throwable) {
        super(message, throwable);
        this.message = message;
    }
    public DefaultApiException(String message) {
        super(message);
        this.message = message;
    }

    public DefaultApiException(StatusCode statusCode){
        super(statusCode.getStatusDescription());
        this.errorCode = statusCode.getStatusCode();
        this.message = statusCode.getStatusDescription();
    }

    public DefaultApiException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
        this.message = message;
    }

    public DefaultApiException(String errorCode, String message, Throwable throwable) {
        super(message, throwable);
        this.errorCode = errorCode;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public String getErrorCode(){
        return  errorCode;
    }
}
