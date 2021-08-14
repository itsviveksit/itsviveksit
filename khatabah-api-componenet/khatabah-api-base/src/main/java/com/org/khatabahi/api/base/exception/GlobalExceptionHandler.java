package com.org.khatabahi.api.base.exception;

import com.org.khatabahi.api.base.exception.handler.BaseExceptionHandler;
import com.org.khatabahi.api.base.exception.handler.ExceptionDealerMapper;
import com.org.khatabahi.api.base.exception.handler.ExceptionHandlerFactory;
import org.apache.camel.CamelExecutionException;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.RuntimeExchangeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component("globalExceptionHandler")
public class GlobalExceptionHandler implements Processor {

    @Autowired
    private ExceptionHandlerFactory exceptionHandlerFactory;

    @Autowired
    private ExceptionDealerMapper exceptionDealerMapper;

    @Override
    public void process(Exchange exchange) throws Exception {
        Optional<Exception> rootCauseOptional = Optional.ofNullable(exchange.getProperty(Exchange.EXCEPTION_CAUGHT, CamelExecutionException.class))
                .map(RuntimeExchangeException::getExchange)
                .map(rootCause -> rootCause.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class));
        Exception cause = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class);
        if(rootCauseOptional.isPresent() && rootCauseOptional.get() instanceof org.apache.http.conn.ConnectTimeoutException){
            cause = rootCauseOptional.get();
        }
        BaseExceptionHandler exceptionHandler = lookupExceptionHandler(cause);
        exceptionHandler.handleException(exchange);

    }

    private BaseExceptionHandler lookupExceptionHandler(Exception cause) {
        String exceptionHandlerName = exceptionDealerMapper.getExceptionHandlerByExceptionType(cause.getClass());
        return exceptionHandlerFactory.getExceptionHandler(exceptionHandlerName);
    }
}

