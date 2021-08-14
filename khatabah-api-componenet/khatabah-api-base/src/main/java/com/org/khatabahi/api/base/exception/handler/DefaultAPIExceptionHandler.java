package com.org.khatabahi.api.base.exception.handler;


import com.org.khatabahi.api.base.exception.annotation.ExceptionDealer;
import com.org.khatabahi.common.base.response.GenericResponse;
import com.org.khatabahi.common.constant.StatusCode;
import com.org.khatabahi.common.exception.DefaultApiException;
import org.apache.camel.Exchange;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@ExceptionDealer(
        exceptionTypes = {DefaultApiException.class}
)
@Component("DefaultApiExceptionHandler")
public class DefaultAPIExceptionHandler extends BaseExceptionHandler {

    @Override
    public String generateResponse(Exchange exchange) {
        String errorCode = StatusCode.GENERIC_FAILURE_COM_ERR_199.getStatusCode();
        String errorDesc = StatusCode.GENERIC_FAILURE_COM_ERR_199.getStatusDescription();
        Exception cause = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class);
        if (cause instanceof DefaultApiException) {
            DefaultApiException exp = DefaultApiException.class.cast(cause);
            errorCode = exp.getErrorCode();
            errorDesc = exp.getMessage();
        }
        List<String> errors = Arrays.asList(errorDesc);
        return generateErrors(exchange, errorCode, errors);
    }
}
