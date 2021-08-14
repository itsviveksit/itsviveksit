package com.org.khatabahi.common.exception;

import com.org.khatabahi.common.constant.StatusCode;

public class BaseException extends Exception{
    private transient StatusCode statusCode;
    private transient Object object;
    protected BaseException(){
        super();
        this.statusCode = null;
    }

    protected BaseException(String strMsg){
      super(strMsg);
    }

    public BaseException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public BaseException(StatusCode statusCode) {
        super();
        this.statusCode = statusCode;
    }

    public BaseException(StatusCode responseCode, String message) {
        super(message);
        this.statusCode = responseCode;
    }

    public BaseException(StatusCode statusCode, String message, Throwable cause) {
        super(message, cause);
        this.statusCode = statusCode;
    }

    public BaseException(StatusCode statusCode, Throwable cause) {
        super(cause);
        this.statusCode = statusCode;
    }

    @SuppressWarnings("unchecked")
    public <T> T getObject(){
        this.object = object;
        return ((T)this);
    }
    @SuppressWarnings("unchecked")
    public <T extends BaseException> T setObject(Object object){
        this.object = object;
        return ((T)this);
    }

    @Override
    public String getMessage() {
        String  mes = super.getMessage();
        String sc = statusCode == null ? "" : "[" + statusCode + "]";
        return mes + sc;
    }
}
