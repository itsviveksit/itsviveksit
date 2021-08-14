package com.org.khatabahi.api.base.exception.handler;

import com.org.khatabahi.common.base.response.GenericResponse;
import com.org.khatabahi.common.constant.StatusCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Component("defaultExceptionHandler")
public class DefaultExceptionHandler extends BaseExceptionHandler {

    @Override
    public String generateResponse(Exchange exchange) {
        String errorCode = StatusCode.GENERIC_FAILURE_COM_ERR_199.getStatusCode();
        String errorDesc = StatusCode.GENERIC_FAILURE_COM_ERR_199.getStatusDescription();
        List<String> errors = Arrays.asList(errorDesc);

        return generateErrors(exchange, errorCode, errors);
    }
}
