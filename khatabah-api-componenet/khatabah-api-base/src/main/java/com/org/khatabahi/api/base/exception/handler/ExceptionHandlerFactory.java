package com.org.khatabahi.api.base.exception.handler;



/**
 * A interface follows Service Locator Spring's design pattern. This help to lookup the
 * exception handler by spring's bean id
 */
public interface ExceptionHandlerFactory {
    BaseExceptionHandler getExceptionHandler(String exceptionHandlerType);
}
