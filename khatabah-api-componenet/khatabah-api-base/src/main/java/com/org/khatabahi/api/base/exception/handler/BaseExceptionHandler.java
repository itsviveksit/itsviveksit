package com.org.khatabahi.api.base.exception.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.org.khatabahi.common.base.request.GenericRequest;
import com.org.khatabahi.common.base.request.GenericRequestContext;
import com.org.khatabahi.common.base.request.GenericRequestHeader;
import com.org.khatabahi.common.base.response.GenericResponse;
import com.org.khatabahi.common.base.response.GenericResponseContext;
import com.org.khatabahi.common.base.response.GenericResponseHeader;
import com.org.khatabahi.common.utils.InstanceUtils;
import com.org.khatabahi.common.utils.KhatabahiRequestResponseUtils;
import com.org.khatabahi.logger.KhatabahiLogger;
import org.apache.camel.Exchange;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public abstract class BaseExceptionHandler {


    private static KhatabahiLogger log = KhatabahiLogger.getLogger(BaseExceptionHandler.class);
    protected static final ObjectMapper MAPPER = new ObjectMapper();

    public void handleException(Exchange exchange) {
        exchange.getIn().setBody(generateResponse(exchange));
    }

    protected String generateErrors(Exchange exchange, String statusCode, List<String> errors) {
        this.clearMultipartHeaders(exchange);
        Exception cause = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class);
        log.error("Exception occurs: ", cause);
        HttpServletResponse httpServletResponse = exchange.getIn().getBody(HttpServletResponse.class);
        String reqString = exchange.getIn().getBody(String.class);
        String message = null;
        if (errors != null) {
            message =String.join(", ", errors);
        }
        try {
            return KhatabahiRequestResponseUtils.createResponse(reqString, statusCode, message);
        } catch (IOException e) {
            log.error("BaseExptione handler error " , e);
        }
        GenericResponseHeader responseHeader = new GenericResponseHeader();
        GenericResponseContext responseContext = new GenericResponseContext();
        responseContext.setResponseDateTime(new Date().toString());
        responseContext.setStatusCode(statusCode);
        responseContext.setStatusDescription(message);
        responseHeader.setResponseContext(responseContext);

        GenericResponse response = new GenericResponse();
        response.setServiceResponseHeader(responseHeader);
        try{
            return InstanceUtils.getObjectMapperInstance().writeValueAsString(response);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void clearMultipartHeaders(Exchange exchange){
        exchange.getIn().removeHeaders("CamelHttp*");
    }

    public String toJSON(Object object) {
        String jsonResponse = null;
        try {
            jsonResponse = MAPPER.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("Unable to parse object: ", object);
            //throw new JsonConversionException("Unable to parse error response body to json", e);
        }
        return jsonResponse;
    }

    public abstract String generateResponse(Exchange exchange);
}
