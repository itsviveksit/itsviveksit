package com.org.khatabahi.api.base.exception.handler;/*
package com.org.khatabahi.api.base.exception.handler;

import com.netflix.hystrix.exception.HystrixBadRequestException;
import com.org.khatabahi.api.base.exception.annotation.ExceptionDealer;
import com.org.khatabahi.common.base.response.GenericResponse;
import com.org.khatabahi.common.constant.StatusCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

*/
/**
 * This class handles all Hystrix related exception
 *//*

@ExceptionDealer(exceptionTypes = {HystrixBadRequestException.class})
@Slf4j
@Component("HystrixExceptionHandler")
public class HystrixExceptionHandler extends BaseExceptionHandler {
    @Override
    public String generateResponse(Exchange exchange) {
        String errorCode = StatusCode.GENERIC_DATA_ACCESS_TIMEOUT_ERR.getStatusCode();
        String errorDesc = StatusCode.GENERIC_DATA_ACCESS_TIMEOUT_ERR.getStatusDescription();
        List<String> errors = Arrays.asList(errorDesc);

        GenericResponse response = generateErrors(exchange, errorCode, errors);
        return toJSON(response);
    }
}
*/
