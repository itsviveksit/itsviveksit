package com.org.khatabahi.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.org.khatabahi.common.base.request.GenericRequest;
import com.org.khatabahi.common.base.request.GenericRequestContext;
import com.org.khatabahi.common.base.request.GenericRequestHeader;
import com.org.khatabahi.common.base.response.GenericResponse;
import com.org.khatabahi.common.base.response.GenericResponseContext;
import com.org.khatabahi.common.base.response.GenericResponseHeader;
import com.org.khatabahi.common.constant.StatusCode;
import com.org.khatabahi.common.exception.DefaultApiException;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.BindException;
import java.util.*;
import java.util.stream.Collectors;

@Order(Ordered.HIGHEST_PRECEDENCE)
@EnableWebMvc
@ControllerAdvice
public class GlobalExceptionHandlerTest extends ResponseEntityExceptionHandler {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private static String getValidationMessage(String field, String detail){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[")
                .append(field).append(" : ")
                .append(detail).append("]");
        return stringBuilder.toString();
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class, BindException.class})
    public ResponseEntity<Object> methodArgumentNotValidException(HttpServletRequest request, MethodArgumentNotValidException exception){
        Optional<BindingResult> bindingResult = Optional.ofNullable(exception.getBindingResult());
        List<String> fieldErrors = bindingResult.map(Errors::getFieldErrors).orElse(Collections.emptyList())
                .stream().map(
                        error -> getValidationMessage(error.getObjectName() + "." + error.getField() + "." + error.getCode(),error.getDefaultMessage())
                ).collect(Collectors.toList());

        List<String> globalErrors = bindingResult.map(Errors::getGlobalErrors).orElse(Collections.emptyList())
                .stream().map(
                        error -> getValidationMessage(error.getObjectName() + "." + error.getCode(),error.getDefaultMessage())
                ).collect(Collectors.toList());

        fieldErrors.addAll(globalErrors);
        GenericResponse response = generateError(request, "COM-ERR-199-002", fieldErrors);
        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);
    }

    @ExceptionHandler({DefaultApiException.class})
    public final ResponseEntity<Object> handleDefaultApiException(Exception exception, HttpServletRequest request){
        String errorCode = StatusCode.GENERIC_FAILURE_COM_ERR_199.getStatusCode();
        String errorDesc = StatusCode.GENERIC_FAILURE_COM_ERR_199.getStatusDescription();
        if(exception instanceof DefaultApiException){
            DefaultApiException defaultApiException = (DefaultApiException)exception;
            if(StringUtils.isNotEmpty(defaultApiException.getErrorCode())){
                errorCode = defaultApiException.getErrorCode();
            }
            errorDesc = defaultApiException.getMessage();
        }
        List<String> errors = Arrays.asList(errorDesc);
        GenericResponse response = generateError(request, errorCode, errors);
        HttpStatus httpStatus = HttpStatus.OK;
        return new ResponseEntity<>(response, new HttpHeaders(), httpStatus);
    }

    @ExceptionHandler({Exception.class})
    public final ResponseEntity<Object> handleAllException(Exception exception, HttpServletRequest request){
        String errorCode = StatusCode.GENERIC_FAILURE_COM_ERR_199.getStatusCode();
        String errorDesc = StatusCode.GENERIC_FAILURE_COM_ERR_199.getStatusDescription();
        List<String> errors = Arrays.asList(errorDesc);
        GenericResponse response = generateError(request, errorCode, errors);
        HttpStatus httpStatus = HttpStatus.OK;
        return new ResponseEntity<>(response, new HttpHeaders(), httpStatus);
    }

    private GenericResponse generateError(HttpServletRequest request, String statusCode, List<String> errors) {
        GenericRequest req = null;
        try{
            String requestStr = IOUtils.toString(request.getReader());
            req = MAPPER.readValue(requestStr, GenericRequest.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        GenericRequestContext reqContext = Optional.ofNullable(req)
                .map(GenericRequest.class::cast)
                .map(GenericRequest::getServiceRequestHeader)
                .map(GenericRequestHeader.class::cast)
                .map(GenericRequestHeader::getRequesterContext)
                .map(GenericRequestContext.class::cast).orElse(null);

        GenericResponseHeader resHeader = new GenericResponseHeader();
        GenericResponseContext resContext = new GenericResponseContext();

        String message = "";
        if(errors != null && errors.size()>0){
            message = String.join(", ", errors);
        }
        resContext.setResponseDateTime(new Date().toString());
        resContext.setStatusCode(statusCode);
        resContext.setStatusDescription(message);

        resHeader.setRequesterContext(reqContext);
        resHeader.setResponseContext(resContext);

        GenericResponse response = new GenericResponse();
        response.setServiceResponseHeader(resHeader);
        return response;
    }
}
